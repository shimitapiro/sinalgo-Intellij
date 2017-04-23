/*
 Copyright (c) 2007, Distributed Computing Group (DCG)
                    ETH Zurich
                    Switzerland
                    dcg.ethz.ch

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 - Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the
   distribution.

 - Neither the name 'Sinalgo' nor the names of its contributors may be
   used to endorse or promote products derived from this software
   without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package projects.BFS;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import projects.BFS.nodes.nodeImplementations.LeafNode;
import projects.BFS.nodes.nodeImplementations.TreeNode;

import sinalgo.configuration.Configuration;
import sinalgo.nodes.Node;
import sinalgo.runtime.AbstractCustomGlobal;
import sinalgo.runtime.Runtime;
import sinalgo.tools.Tools;

/**
 * This class holds customized global state and methods for the framework. 
 * The only mandatory method to overwrite is 
 * <code>hasTerminated</code>
 * <br>
 * Optional methods to override are
 * <ul>
 * <li><code>customPaint</code></li>
 * <li><code>handleEmptyEventQueue</code></li>
 * <li><code>onExit</code></li>
 * <li><code>preRun</code></li>
 * <li><code>preRound</code></li>
 * <li><code>postRound</code></li>
 * <li><code>checkProjectRequirements</code></li>
 * </ul>
 * @see AbstractCustomGlobal for more details.
 * <br>
 * In addition, this class also provides the possibility to extend the framework with
 * custom methods that can be called either through the menu or via a button that is
 * added to the GUI. 
 */
public class CustomGlobal extends AbstractCustomGlobal{


	/* (non-Javadoc)
	 * @see runtime.AbstractCustomGlobal#hasTerminated()
	 */
	public boolean hasTerminated() {
		return false;
	}

	Random r = new Random();
	/**
	 * Dummy button to create a tree.  
	 */
	@CustomButton(buttonText="Build A new random Tree", toolTipText="Builds Random tree")
	public void sampleButton() {

		int Low = 10;
		int High = 50;
		int vertexts = r.nextInt(High-Low) + Low;

		if(vertexts % 10 != 0){
			vertexts = vertexts + (10 - (vertexts % 10));
		}
		buildTree(vertexts);
	}
	


	
	// a vector of all non-leaf nodes
	Vector<TreeNode> treeNodes = new Vector<TreeNode>();

	/**
	 * Builds a tree for the specified number of leaves and
	 * fan-out, and removes all nodes in the framework that were 
	 * added prior to this method call. 
	 * <p>
	 * The method places all leaves on a line at the bottom of the screen
	 * and builds a balanced tree on top (bottom up), such that each tree-node
	 * is is parent of fanOut children. 
	 *
	 */
	public void buildTree(int vertexes) {

		Tools.showMessageDialog("Building a Graph with "+vertexes +" Vertexes.");

		
		// remove all nodes (if any)
		Runtime.clearAllNodes();
		treeNodes.clear();


		// some vectors to store the nodes that we still need to process
		ArrayList<ArrayList<TreeNode>> allVertexes = new ArrayList<ArrayList<TreeNode>>();
		Vector<TreeNode> swap;

		int vertexsDivided = vertexes/10;
		double dx = ((double) Configuration.dimX) / (vertexsDivided + 1); // distance between two leaf-nodes
		double posY = Configuration.dimY - 30; // y-offset of all leave nodes


		for (int j=0;j<10;j++){
			allVertexes.add(new ArrayList<TreeNode>());
			// create the leaves (incl. assigning their position)
			for(int i=0; i<vertexsDivided; i++) {
				TreeNode ln = new TreeNode();
				ln.setPosition((i+1)*dx, posY, 0);
				ln.finishInitializationWithDefaultModels(true);
				allVertexes.get(j).add(ln);
			}
			posY -= 100;
		}
		int Low = 10;
		int High = 100;
		for (int j=0;j<10;j++) {
			// create the leaves (incl. assigning their position)
			for (int i = 0; i < vertexsDivided; i++) {

				boolean didAnEdge = false;

				int matchConnection = r.nextInt(High-Low) + Low;
				if (matchConnection > 70) {
					if (allVertexes.size() > (j + 1)) {//above
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j + 1).get(i));
						didAnEdge = true;
					}
				}

				matchConnection = r.nextInt(High-Low) + Low;
				if (matchConnection > 70) {
					if ((j - 1) > 0) {//lower
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j - 1).get(i));
						didAnEdge = true;
					}
				}
				matchConnection = r.nextInt(High-Low) + Low;
				if (matchConnection > 70) {
					if ((j - 1) > 0 && allVertexes.get(j).size() > (i + 1)) {//lower left
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j - 1).get(i + 1));
						didAnEdge = true;
					}
				}
				matchConnection = r.nextInt(High-Low) + Low;
				if (matchConnection > 70) {
					if ((j - 1) > 0 && (i - 1) >= 0) {//lower left
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j - 1).get(i - 1));
						didAnEdge = true;
					}
				}
				matchConnection = r.nextInt(High-Low) + Low;
				if (matchConnection > 70) {
					if (allVertexes.size() > (j + 1) && (i - 1) >= 0) {//lower left
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j + 1).get(i - 1));
						didAnEdge = true;
					}
				}
				matchConnection = r.nextInt(High-Low) + Low;
				if (matchConnection > 70) {
					if (allVertexes.size() > (j + 1) && allVertexes.get(j).size() > (i + 1)) {//lower left
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j + 1).get(i + 1));
						didAnEdge = true;
					}
				}

				if( !didAnEdge ){
					if (allVertexes.get(j).size() > (i + 1)) {  //right
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j).get(i + 1));
						didAnEdge = true;
					}

					if (!didAnEdge && (i - 1) >= 0) {//left
						allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j).get(i - 1));
						didAnEdge = true;
					}
				}else {
					matchConnection = r.nextInt(High-Low) + Low;
					if (matchConnection > 70) {
						if (allVertexes.get(j).size() > (i + 1)) {  //right
							allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j).get(i + 1));
							didAnEdge = true;
						}
					}
					matchConnection = r.nextInt(High-Low) + Low;
					if (matchConnection > 70) {
						if ((i - 1) >= 0) {//left
							allVertexes.get(j).get(i).addConnectionTo(allVertexes.get(j).get(i - 1));
							didAnEdge = true;
						}
					}

				}

			}
		}


		
		// Repaint the GUI as we have added some nodes
		Tools.repaintGUI();
	}

	public void preRun() {
		// A method called at startup, before the first round is executed.

	}
}
