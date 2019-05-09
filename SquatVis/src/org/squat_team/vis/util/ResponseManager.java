package org.squat_team.vis.util;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.squat_team.vis.connector.protocols.LevelResponseServerProtocol;

/**
 * Stores the {@link LevelResponseServerProtocol} that wait for a response
 * provided by the architect. Wakes up and notifies protocols that have a
 * response available now. The {@link ResponseIdentifier} is used to identify
 * the response.
 */
@Named
@ApplicationScoped
public class ResponseManager {
	private Map<ResponseIdentifier, LevelResponseServerProtocol> protocolsWaitingForResponse = new HashMap<>();

	/**
	 * Registers a protocol that is waiting for a response.
	 * 
	 * @param responseIdentifier identifies the response the protocol is waiting for
	 * @param protocol           the protocol
	 */
	public synchronized void waitForResponse(ResponseIdentifier responseIdentifier,
			LevelResponseServerProtocol protocol) {
		protocolsWaitingForResponse.put(responseIdentifier, protocol);
	}

	/**
	 * Wakes up the protocol if the response is stored that is required by the
	 * protocol.
	 * 
	 * @param responseIdentifier identifies the response the protocol is waiting for
	 */
	public synchronized void listenToResponseArrived(@Observes ResponseIdentifier responseIdentifier) {
		LevelResponseServerProtocol protocol = protocolsWaitingForResponse.remove(responseIdentifier);
		if (protocol != null) {
			synchronized (protocol) {
				protocol.notifyAll();
			}
		}
	}

}
