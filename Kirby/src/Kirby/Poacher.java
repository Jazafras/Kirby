package Tigress;
	
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import jig.Vector;

 class Poacher extends MovingEntity {
	
	public static final String[] facingImages = 
		{
			TigressGame.POACHER_LEFTIMG_RSC,
			TigressGame.POACHER_LEFTIMG_RSC,
			TigressGame.POACHER_LEFTIMG_RSC,
			TigressGame.POACHER_LEFTIMG_RSC/*,
			TigressGame.TIGRESS_RIGHTIMG_RSC,
			TigressGame.TIGRESS_FRONTIMG_RSC,
			TigressGame.TIGRESS_BACKIMG_RSC*/
		};
	
	private boolean trapped;
	private Vertex nextPos;
	private String direction;
	private boolean firstPath;
	private boolean reset;

	public Poacher(final float x, final float y, Vertex pos) { 
		super(x, y, facingImages, LEFT);
		setVelocity(new Vector(0, 0));
		trapped = false;
		vPos = pos;
		firstPath = true;
		reset = false;
	}
	
	public void setMoving(TigressGame bg) {
		if (hasPassed() || firstPath || reset) {
			Map<String, Integer> distances = getDistances(bg);
			int minDist = Integer.MAX_VALUE;
			Vertex closest = null;
			for (Cub c : bg.cubs) {
				if (!c.isHeld()) {
					Vertex pos = c.getVertex();
					if (pos != null && bg.vPos.containsKey(pos.toString()) &&
							distances.get(pos.toString()) != null &&
							distances.get(pos.toString()) < minDist) {
						minDist = distances.get(pos.toString());
						closest = pos;
					}
				}
			}
			if (bg.tigress.getVertex() != null && 
					bg.vPos.containsKey(bg.tigress.getVertex().toString()) &&
					distances.get(bg.tigress.getVertex().toString()) != null &&
					distances.get(bg.tigress.getVertex().toString()) < minDist) {
				minDist = distances.get(bg.tigress.getVertex().toString());
				closest = bg.tigress.getVertex();
			}
			
			LinkedList<Vertex> path;
			if (!firstPath && !reset) {
				path = search(getNextPos(), closest);
				vPos = nextPos;
			} else {
				path = search(vPos, closest);
				firstPath = false;
				reset = false;
			}
			if (path != null && path.size() > 0)
				nextPos = path.get(0);
			if (nextPos != null && nextPos.getX() > vPos.getX()) {
				setVelocity(new Vector(.07f, 0f));
				direction = "right";
			} else if (nextPos != null && nextPos.getX() < vPos.getX()) {
				setVelocity(new Vector(-.07f, 0f));
				direction = "left";
			} else if (nextPos != null && nextPos.getY() > vPos.getY()) {
				setVelocity(new Vector(0f, .07f));
				direction = "below";
			} else if (nextPos != null && path != null) {
				setVelocity(new Vector(0f, -.07f));
				direction = "above";
			} else {
				setVelocity(new Vector(0f, 0f));
				direction = "none";
			}
			/*System.out.println("path: " + path);
			System.out.println("currentpos: " + vPos.toString());
			System.out.println("nextpos: " + nextPos.toString());
			System.out.println("closest: " + closest);
			System.out.println("direction: " + direction);
			System.out.println("--------------------------------------------");*/
		}
	}	
	
	private boolean hasPassed() {
		if (direction != null && nextPos != null) {
			if (direction.equals("left"))
				return getPosition().getX() <= nextPos.getX();
			else if (direction.equals("right"))
				return getPosition().getX() >= nextPos.getX();
			else if (direction.equals("above"))
				return getPosition().getY() <= nextPos.getY();
			else
				return getPosition().getY() >= nextPos.getY();
		}
		return false;
	}
	
	/**
	 * Set whether the poacher is trapped in a vine or not.
	 * @param val: true if yes, false if not
	 */
	public void setTrapped(boolean val) {
		trapped = val;
	}
	
	/**
	 * @return trapped: true if trapped in vine, false if not
	 */
	public boolean isTrapped() {
		return trapped;
	}
	
	public Vertex getCurrentPos() {
		return vPos;
	}
	
	public Vertex getNextPos() {
		return nextPos;
	}
	
	private Map<String, Integer> getDistances(TigressGame bg) {
		Queue<Vertex> frontier = new LinkedList<Vertex>();
		frontier.add(bg.poacher.getCurrentPos());
		Map<String, Integer> distance = new HashMap<String, Integer>();
		distance.put(bg.poacher.getCurrentPos().toString(), 0);
		
		while (!frontier.isEmpty()) {
			Vertex cur = frontier.poll();
			for (Vertex n : cur.getNeighbors()) {
				if (!distance.containsKey(n.toString())) {
					frontier.add(n);
					distance.put(n.toString(), 1 + distance.get(cur.toString()));
				}
			}
		}
		return distance;
	}
	
	public LinkedList<Vertex> search(Vertex start, Vertex goal) {
		LinkedList<Vertex> closedList = new LinkedList<Vertex>();
		LinkedList<Vertex> openList = new LinkedList<Vertex>();
		Map<String, Vertex> parents = new HashMap<String, Vertex>();
		if (start != null && goal != null) {
			openList.add(start);
			parents.put(start.toString(), null);
	 
			while (!openList.isEmpty()) {
				Vertex v = openList.removeFirst();
				if (v != null && v.toString().equals(goal.toString())) {
					return constructPath(goal, parents);
				} else if (v != null) {
					closedList.add(v);
					  
					for (Vertex neighbor : v.getNeighbors()) {
						if (!closedList.contains(neighbor) && !openList.contains(neighbor)) {
							parents.put(neighbor.toString(), v);
							openList.add(neighbor);
						}
					}
				}
			}
		}
		  
		return null;
	}
	
	protected LinkedList<Vertex> constructPath(Vertex v, Map<String, Vertex> parents) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		while (parents.get(v.toString()) != null) {
			path.addFirst(v);
			v = parents.get(v.toString());
		}
		return path;
	}
	
	protected void setReset(TigressGame bg) {
		reset = true;
		Vertex v = new Vertex(50, 50);
		vPos = bg.vPos.get(v.toString());
		nextPos = null;
	}
	
}
