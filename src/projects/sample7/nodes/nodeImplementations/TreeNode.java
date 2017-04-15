package projects.sample7.nodes.nodeImplementations;



import java.awt.Color;

import projects.defaultProject.nodes.timers.MessageTimer;
import projects.sample7.nodes.messages.MarkMessage;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

/**
 * An internal node (or leaf node) of the tree.
 * Note that the leaves are instances of LeafNode, a subclass of this class. 
 */
public class TreeNode extends Node {

	public projects.sample7.nodes.nodeImplementations.TreeNode parent = null; // the parent in the tree, null if this node is the root
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void handleMessages(Inbox inbox) {
		while(inbox.hasNext()) {
			Message m = inbox.next();
			if(m instanceof MarkMessage) {
				if(parent == null || !inbox.getSender().equals(parent)) {
					continue;// don't consider mark messages sent by children
				}
				this.setColor(Color.BLUE);
				// forward the message to all children

				MarkMessage msg = new MarkMessage();
				MessageTimer timer = new MessageTimer(msg);
				timer.startRelative(5000, this);
			}
		}
	}

	@Override
	public void init() {

	}



	@Override
	public void neighborhoodChange() {
		//If i'm the parent vroadcast to all my children flood
		if (parent == null ){
			MarkMessage msg = new MarkMessage();
			this.broadcast(msg);
		}
	}

	@Override
	public void preStep() {
	}

	@Override
	public void postStep() {
	}
	


}
