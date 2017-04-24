package projects.EX12.nodes.nodeImplementations;



import java.awt.Color;

import projects.defaultProject.nodes.timers.MessageTimer;
import projects.EX12.nodes.messages.MarkMessage;
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

	public projects.EX12.nodes.nodeImplementations.TreeNode parent = null; // the parent in the tree, null if this node is the root
	private Color myColor = null;


	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	@Override
	public void handleMessages(Inbox inbox) {
		while(inbox.hasNext()) {
			Message m = inbox.next();
			if(m instanceof MarkMessage) {
				//if msg from children dont do anything
				if (!inbox.getSender().equals(parent)){
					return;
				}
				//i'm the root of the tree
				if(parent == null ) {
					myColor = Color.green;
				}else {
					if (((MarkMessage) m).getColor() != null &&
							((MarkMessage) m).getColor().equals(Color.green)){
						myColor = Color.orange;
					}else {
						myColor = Color.green;
					}
				}
				setColor(myColor);

				SendMsgToAllChildren();

			}
		}
	}

	private void SendMsgToAllChildren(){
		Message msg = new MarkMessage(myColor);
		// forward the message to all children
		for(Edge e : outgoingConnections) {
			if(!e.endNode.equals(parent)) { // don't send it to the parent
				send(msg, e.endNode);
			}
		}
	}

	@Override
	public void init() {
	}

	@Override
	public void neighborhoodChange() {
	}

	@Override
	public void preStep() {
		//i'm the root of the tree
		if(parent == null && myColor == null) {
			myColor = Color.green;
			setColor(myColor);

			SendMsgToAllChildren();
		}

	}

	@Override
	public void postStep() {
	}
	


}
