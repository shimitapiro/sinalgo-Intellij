package projects.broadcast.nodes.nodeImplementations;



import java.awt.*;

import projects.defaultProject.nodes.timers.MessageTimer;
import projects.broadcast.nodes.messages.MarkMessage;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

/**
 * An internal node (or leaf node) of the tree.
 * Note that the leaves are instances of LeafNode, a subclass of this class. 
 */
public class TreeNode extends Node {

	public projects.broadcast.nodes.nodeImplementations.TreeNode parent = null; // the parent in the tree, null if this node is the root
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}
	boolean initRoot = false;

	@Override
	public void handleMessages(Inbox inbox) {
		if (parent == null && !initRoot){
			initRoot = true;
			MarkMessage msg = new MarkMessage();
			this.broadcast(msg);
			this.setColor(Color.BLUE);
		}else {
			while(inbox.hasNext()) {
				Message m = inbox.next();
				if(m instanceof MarkMessage) {
					if(parent == null || !inbox.getSender().equals(parent)) {
						continue;// don't consider mark messages sent by children
					}
					this.setColor(Color.GREEN);
					// forward the message to all children

					for(Edge e : outgoingConnections) {
						if(!e.endNode.equals(parent)) { // don't send it to the parent
//							send(m, e.endNode);
							MarkMessage msg = new MarkMessage();

							MessageTimer timer = new MessageTimer(msg,e.endNode);
							timer.startRelative(50, this);
						}
					}

				}
			}

		}

	}

	public void draw(Graphics g, PositionTransformation pt, boolean highlight){
		super.drawNodeAsDiskWithText(g, pt, highlight, "", 15, Color.YELLOW);
	}


	@Override
	public void init() {

	}



	@Override
	public void neighborhoodChange() {

	}

	@Override
	public void preStep() {
	}

	@Override
	public void postStep() {
	}
	


}
