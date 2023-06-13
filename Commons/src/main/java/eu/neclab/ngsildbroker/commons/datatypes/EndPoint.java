package eu.neclab.ngsildbroker.commons.datatypes;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import eu.neclab.ngsildbroker.commons.constants.NGSIConstants;

/**
 * @author hebgen
 * @version 1.0
 * @created 11-Jun-2018 11:13:22
 */

public class EndPoint implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7535049847687307441L;
	private String accept;
	private URI uri;
	private Map<String, String> notifierInfo;
	private ArrayListMultimap<String, String> receiverInfo;

	public EndPoint() {
		notifierInfo = new HashMap<>();
		notifierInfo.put(NGSIConstants.MQTT_VERSION, NGSIConstants.MQTT_VERSION_5);
		notifierInfo.put(NGSIConstants.MQTT_QOS, NGSIConstants.DEFAULT_MQTT_QOS.toString());
	}

	public EndPoint(EndPoint endPoint) {
		this.accept = endPoint.accept;
		try {
			this.uri = new URI(endPoint.uri.toString());
		} catch (URISyntaxException e) {
			// will never happen
		}
		if (endPoint.notifierInfo != null) {
			this.notifierInfo = new HashMap<String, String>(endPoint.notifierInfo);
		}
	}

	public void update(EndPoint endPoint) {
		if (endPoint.accept != null) {
			this.accept = endPoint.accept;
		}
		if (endPoint.uri != null) {
			try {
				this.uri = new URI(endPoint.uri.toString());
			} catch (URISyntaxException e) {
				// will never happen
			}
		}
		if (endPoint.notifierInfo != null) {
			this.notifierInfo = new HashMap<String, String>(endPoint.notifierInfo);
		}

	}

	public void finalize() throws Throwable {

	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public Map<String, String> getNotifierInfo() {
		return notifierInfo;
	}

	public void setNotifierInfo(Map<String, String> notifierInfo) {
		this.notifierInfo = notifierInfo;
	}

	public void setReceiverInfo(ArrayListMultimap<String, String> receiverInfo) {
		this.receiverInfo = receiverInfo;
	}

	public ArrayListMultimap<String, String> getReceiverInfo() {
		return receiverInfo;
	}

	@Override
	public String toString() {
		return "EndPoint [accept=" + accept + ", uri=" + uri + ", notifierInfo=" + notifierInfo + ", receiverInfo="
				+ receiverInfo + "]";
	}

}