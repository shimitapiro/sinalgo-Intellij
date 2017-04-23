package projects.BFS.nodes.nodeImplementations;



import java.awt.*;

import projects.defaultProject.nodes.timers.MessageTimer;
import projects.BFS.nodes.messages.MarkMessage;
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

	
	@Override
	public void checkRequirements() throws WrongConfigurationException {
	}

	boolean Initiator = false;
	TreeNode parent = null;
	int layer = Integer.MAX_VALUE;

	public boolean hasParent(){
		if (parent != null){
			return true;
		}
		return false;
	}

	@Override
	public void handleMessages(Inbox inbox) {
		if (Initiator){
			Initiator = false;
			MarkMessage msg = new MarkMessage();
			msg.setSender(this);
			msg.setLayer(0);
			this.broadcast(msg);
			this.setColor(Color.GREEN);
		}else {
			while(inbox.hasNext()) {
				Message m = inbox.next();
				if(m instanceof MarkMessage) {
					//if this layer is bigger then minde move on
					if(((MarkMessage) m).getLayer() < layer) {
						System.out.println("Layer from = "+((MarkMessage) m).getLayer() +"My Layer ="+layer);

						if ( (((MarkMessage) m).getLayer() % 2) == 0) {
							this.setColor(Color.GREEN);
						} else if ( (((MarkMessage) m).getLayer() % 2) == 1) {
							this.setColor(Color.PINK);
						}


						MarkMessage msg = new MarkMessage();
						msg.setSender(this);
						layer = (((MarkMessage) m).getLayer() + 1);
						msg.setLayer(layer);
						parent = ((MarkMessage) m).getSender();

						for (Edge e : outgoingConnections) {
							if (!e.endNode.equals(parent)) { // don't send it to the parent
								Color color = e.defaultColor;
								this.send(msg, e.endNode);
								e.defaultColor = color;
							} else {
								e.defaultColor = Color.GREEN;

							}
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


	@NodePopupMethod(menuText = "Set As Initiator")
	public void Initiator() {
		Initiator = true;
		projects.BFS.nodes.messages.MarkMessage msg = new projects.BFS.nodes.messages.MarkMessage();
		MessageTimer timer = new MessageTimer(msg);
		timer.startRelative(1, this);
	}

}
