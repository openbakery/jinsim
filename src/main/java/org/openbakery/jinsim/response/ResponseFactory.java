package org.openbakery.jinsim.response;

import org.openbakery.jinsim.PacketType;
import org.openbakery.jinsim.response.relay.HostListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * @author Rob Heiser (jinsim@kerf.org)
 * @since 0.001
 */
public class ResponseFactory {
	public static final Logger log = LoggerFactory.getLogger(ResponseFactory.class);
	static private ResponseFactory instance;
	private HashMap<PacketType, Class<? extends InSimResponse>> registeredTypes;

	private ResponseFactory() {
		registeredTypes = new HashMap<PacketType, Class<? extends InSimResponse>>(53);

		registeredTypes.put(PacketType.NONE, NoneResponse.class);
		registeredTypes.put(PacketType.VERSION, VersionResponse.class);
		registeredTypes.put(PacketType.CONNECTION_PLAYER_RENAMED, ConnectionPlayerRenameResponse.class);
		registeredTypes.put(PacketType.TINY, TinyResponse.class);
		registeredTypes.put(PacketType.SMALL, SmallResponse.class);
		registeredTypes.put(PacketType.STATE, StateResponse.class);
		registeredTypes.put(PacketType.CAMERA_POSITION, CameraPositionResponse.class);
		registeredTypes.put(PacketType.START_MULTIPLAYER, MultiplayerBeginResponse.class);
		registeredTypes.put(PacketType.MESSAGE_OUT, MessageResponse.class);
		registeredTypes.put(PacketType.HIDDEN_MESSAGE, HiddenMessageResponse.class);
		registeredTypes.put(PacketType.VOTE_NOTIFICATION, VoteNotificationResponse.class);
		registeredTypes.put(PacketType.RACE_START, RaceStartResponse.class);
		registeredTypes.put(PacketType.NEW_CONNECTION, NewConnectionResponse.class);
		registeredTypes.put(PacketType.CONNECTION_LEFT, ConnectionLeaveResponse.class);
		registeredTypes.put(PacketType.CONNECTION_PLAYER_RENAMED, ConnectionPlayerRenameResponse.class);
		registeredTypes.put(PacketType.NEW_PLAYER, NewPlayerResponse.class);
		registeredTypes.put(PacketType.PLAYER_PIT, PlayerPitsResponse.class);
		registeredTypes.put(PacketType.PLAYER_LEAVE, PlayerLeavingResponse.class);
		registeredTypes.put(PacketType.LAP, LapTimeResponse.class);
		registeredTypes.put(PacketType.SPLIT, SplitTimeResponse.class);
		registeredTypes.put(PacketType.PIT, PitStopResponse.class);
		registeredTypes.put(PacketType.PIT_FINISHED, PitStopFinishedResponse.class);
		registeredTypes.put(PacketType.PIT_LANE, PitLaneResponse.class);
		registeredTypes.put(PacketType.CAMERA_CHANGED, CameraChangedResponse.class);
		registeredTypes.put(PacketType.PENALTY, PenaltyResponse.class);
		registeredTypes.put(PacketType.TAKE_OVER_CAR, TakeOverCarResponse.class);
		registeredTypes.put(PacketType.FLAG, FlagResponse.class);
		registeredTypes.put(PacketType.PLAYER_FLAGS, PlayerFlagResponse.class);
		registeredTypes.put(PacketType.FINISHED_RACE, FinishedRaceResponse.class);
		registeredTypes.put(PacketType.RESULT_CONFIRMED, ResultResponse.class);
		registeredTypes.put(PacketType.REORDER, ReorderResponse.class);
		registeredTypes.put(PacketType.NODE_LAP, NodeLapInfoResponse.class);
		registeredTypes.put(PacketType.MULIT_CAR_INFO, MultiCarInfoResponse.class);
		registeredTypes.put(PacketType.CAR_RESET, CarResetResponse.class);
		registeredTypes.put(PacketType.BUTTON_FUNCTION, ButtonFunctionResponse.class);
		registeredTypes.put(PacketType.BUTTON_CLICKED, ButtonClickedResponse.class);
		registeredTypes.put(PacketType.BUTTON_TYPED, ButtonTypeResponse.class);
		registeredTypes.put(PacketType.RELAY_HOST_LIST_INFO, HostListResponse.class);
		registeredTypes.put(PacketType.AUTOCROSS_LAYOUT, AutocrossLayoutResponse.class);
		registeredTypes.put(PacketType.AUTOCROSS_HIT, AutocrossHit.class);
		registeredTypes.put(PacketType.CONTACT_CAR_CAR, CollisionResponse.class);
	}

	static public ResponseFactory getInstance() {
		if (instance == null) {
			instance = new ResponseFactory();
		}
		return instance;
	}

	public InSimResponse getPacketData(ByteBuffer buffer) throws UnhandledPacketTypeException, BufferUnderflowException, InstantiationException, IllegalAccessException {
		InSimResponse insimResponse = null;

		if (buffer.limit() >= 3) {
			int packetId = buffer.get() & 0xFF;
			PacketType packetType = PacketType.getPacket(packetId);
			/*
			 * if (log.isDebugEnabled()) { log.debug("Packet is of type " + packetType); }
			 */
			Class<? extends InSimResponse> insimResponseClass = registeredTypes.get(packetType);
			if (insimResponseClass == null) {
				buffer.position(buffer.limit());
				throw new UnhandledPacketTypeException(packetId + ": is unknown");
			}
			insimResponse = insimResponseClass.newInstance();
		} else {
			if (log.isDebugEnabled()) {
				String bufferBytes = "";
				for (int i = 0; i < buffer.limit(); i++) {
					bufferBytes += buffer.get() + ", ";
				}
				log.debug("unknown packet: " + bufferBytes);
			} else {
				buffer.position(buffer.limit());
			}
		}

		if (insimResponse == null) {
			throw new UnhandledPacketTypeException("Can not identify response packet");
		}
		try {
			insimResponse.construct(buffer);
		} catch (BufferUnderflowException ex) {
			log.error(ex.getMessage(), ex);
		}
		/*
		 * if (log.isDebugEnabled()) { log.debug("InSimResponse {packet size= " + (buffer.limit()+1) + "}: " + insimResponse); }
		 */
		return insimResponse;
	}
}
