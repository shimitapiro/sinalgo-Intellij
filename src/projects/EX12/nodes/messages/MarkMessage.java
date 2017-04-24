package projects.EX12.nodes.messages;

import sinalgo.nodes.messages.Message;

import java.awt.*;

/**
 * A message sent to children that should be marked.
 */
public class MarkMessage extends Message {

	public MarkMessage() {
	}

	public MarkMessage(Color color) {
		this.color = color;
	}

	@Override
	public Message clone() {
		return this; // read-only policy 
	}

	private Color color = null;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
