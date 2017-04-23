package projects.BFS.nodes.messages;

import projects.BFS.nodes.nodeImplementations.TreeNode;
import sinalgo.nodes.messages.Message;
import sun.reflect.generics.tree.Tree;

/**
 * A message sent to children that should be marked.
 */
public class MarkMessage extends Message {

	int layer = Integer.MAX_VALUE;
	TreeNode sender = null;

	public TreeNode getSender() {
		return sender;
	}

	public void setSender(TreeNode sender) {
		this.sender = sender;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public Message clone() {
		return this; // read-only policy 
	}

}
