/**
 * Protocol class. This class contains string representations of everything that
 * is generalized and agreed upon during the programming project session in week
 * 6.
 * <p>
 * <b>Please note: </b> This code is supposed to be used in accordance with the
 * given documentation (PDF document, most likely). For any questions that are
 * not handled in the documentation or the documentation in this class, please
 * ask Koen van den Brink (programming group 2) for help.
 * </p>
 * <p>
 * Any suggestions to improve the documentation or this code are welcome!
 * </p>
 * 
 * @author Eduard Modreanu and Koen van den Brink
 * @version 1.1
 * @since 2018-01-14
 */
public interface Protocol {
	
	/*
	 * =========== GENERAL ===========
	 */
	
	/**
	 * The information block delimiter. This character should be in between all
	 * your information blocks. E.g.:
	 * <p>
	 * 	 <code>
	 *     String connectPacket = Protocol.CONNECT + Protocol.DELIMITER +
	 *     "Harry" + Protocol.DELIMITER + Protocol.Extensions.CHATTING;
	 * 	 </code>
	 * </p>
	 * This stores a string into <code>connectPacket</code> that tells the server
	 * the client wants to connect with he username "Harry" and that the client
	 * is using the <code>CHATTING</code> extension.
	 */
	public static final String DELIMITER = ";";
	
	/*
	 * =========== ACCEPTANCE AND DENIAL ===========
	 */
	
	/** For general use. Used to refer to acceptance. */
	public static final String ACCEPT	= "0";
	
	/** For general use. Used to refer to denial. */
	public static final String DECLINE	= "1";

	/*
	 * =========== PLAYER TYPES ===========
	 */
	
	/** 
	 * String representation of an AI. Used in the packet where a player can request
	 * the type of player they want to paly against.
	 */
	public static final String COMPUTER_PLAYER	= "0";
	
	/**
	 * String representation of another human player or client. Used in the packet
	 * where a player can request the type of player they want to paly against.
	 */
	public static final String HUMAN_PLAYER	= "1";
	
	/*
	 * =========== MAKING MOVES ===========
	 */
	
	/**
	 * String representation of the starting base. Used in <code>MOVE</code> packets.
	 */
	public static final String STARTING_BASE	= "0";
	
	/**
	 * String representation of a base. Used in <code>MOVE</code> packets.
	 */
	public static final String BASE	= "1";
	
	/**
	 * String representation of the smallest size ring. Used in <code>MOVE</code>
	 * packets.
	 */
	public static final String RING_SMALLEST	= "2";
	
	/**
	 * String representation of the small sized ring. Used in <code>MOVE</code>
	 * packets.
	 */
	public static final String RING_SMALL	= "3";
	
	/**
	 * String representation of the medium sized ring. Used in <code>MOVE</code>
	 * packets.
	 */
	public static final String RING_MEDIUM	= "4";
	
	/**
	 * String representation of the large sized ring. Used in <code>MOVE</code>
	 * packets.
	 */
	public static final String RING_LARGE	= "5";
	
	/*
	 * =========== COLORS ===========
	 */
	
	/**
	 * String representation of the primary color of any player.
	 */
	public static final String PRIMARY	= "0";
	
	/**
	 * String representation of the secondary color of any player.
	 */
	public static final String SECONDARY	= "1";
	
	/*
	 * =========== EXTENSION: CHATTING ===========
	 */
	
	/** Messages sent to everyone logged into the server. */
	public static final String GLOBAL	= "0";
	
	/** Messages sent to everyone in the sender's lobby. */
	public static final String LOBBY	= "1";
	
	/** Messages sent to a given player. */
	public static final String PRIVATE	= "2";
	
	/**
	 * This class contains all string representations of all the game extensions.
	 * There are four extensions:
	 * <ul>
	 * 	 <li>Chatting</li>
	 * 	 <li>Challenging</li>
	 * 	 <li>Leaderboard</li>
	 * 	 <li>Security</li>
	 * </ul>
	 */
	public static interface Extensions {
		
		/**
		 * The chatting extension: clients can send messages to each other via
		 * the server.
		 */
		public static final String EXTENSION_CHATTING = "chat";
		
		/**
		 * The challenging extension: clients can challenge each other via the
		 * server and start a game in that way.
		 */
		public static final String EXTENSION_CHALLENGING = "chal";
		
		/**
		 * The leaderboard extension: clients can request a leaderboard from the
		 * server and a log per player of when that player specifically acquired
		 * certain scores.
		 */
		public static final String EXTENSION_LEADERBOARD = "lead";
		
		/**
		 * The security extension: clients can register a username/password
		 * combination and log in using this combination.
		 */
		public static final String EXTENSION_SECURITY = "secu";
		
	}
	
	/**
	 * This class contains all packet type string representations.
	 */
	public static interface Packets {
		
		/*
		 * =========== CONNECTING ===========
		 */
		
		/**
		 * This packet type is for when the client first achieves a TCP connection
		 * with the server. With this packet, the client can send its extensions
		 * and basically ask to be connected to the server.
		 * If this packet is received on a client from the server, it is the reply
		 * packet to the originally sent packet from the client.
		 */
		public static final String CONNECT = "cn";
		
		/*
		 * =========== REQUESTING A GAME ===========
		 */
		
		/**
		 * This packet type is sent by the client when he requests a game. The packet
		 * will contain preferred amount of players and what type of player the
		 * requester prefers.
		 */
		public static final String GAME_REQUEST = "gr";
		
		/**
		 * This packet type is sent on its own. It tells the receiving client that it
		 * is in a lobby waiting for the game to start.
		 */
		public static final String JOINED_LOBBY = "jl";
		
		/*
		 * =========== STARTING A GAME ===========
		 */
		
		/**
		 * This packet type is sent by the server to all players in a lobby when enough
		 * players connected to that game. It is requesting whether all players are
		 * ready to start playing.
		 */
		public static final String ALL_PLAYERS_CONNECTED = "ap";
		
		/**
		 * This packet type is sent in response by the client. A packet with this type
		 * will contain either <code>ACCEPT</code> or <code>DECLINE</code>
		 */
		public static final String PLAYER_STATUS = "ps";
		
		/**
		 * This packet type is sent by the server to all players in a game when all
		 * players responded with <code>ACCEPT</code> to the server's player status
		 * requests. If one or more players responded with <code>DECLINE</code>, they
		 * are thrown out of the lobby and the remaining players are re-sent a
		 * <code>JOINED_LOBBY</code> packet.
		 */
		public static final String GAME_STARTED = "gs";
		
		/*
		 * =========== PLAYING A GAME ===========
		 */
		
		/**
		 * This packet is sent by the server to the client that has to make a move. The
		 * client has to think for itself what moves it can make. The server will always
		 * check the move and re-send a packet of this type to the client if the move is
		 * invalid.
		 */
		public static final String MAKE_MOVE = "mm";
		
		/**
		 * This move has multiple interpretations:
		 * <p>
		 * If the client sends the server a packet of this type, it is requesting to make
		 * this move
		 * </p>
		 * <p>
		 * If the server sends a client this packet, it means the player that is has the
		 * turn on the client side made this move, and the next player has the turn.
		 * </p>
		 */
		public static final String MOVE = "mv";
		
		/*
		 * =========== GAME ENDS PROPERLY ===========
		 */
		
		/**
		 * This packet type is sent from the server to all players of a certain game. It
		 * contains all the usernames of the players in that game and respectively what
		 * points they acquired in the game.
		 */
		public static final String GAME_ENDED = "ge";
		
		/*
		 * =========== PLAYER DISCONNECTS ===========
		 */
		
		/**
		 * This packet type is sent from the server to all remaining players in a game
		 * when a player in that game disconnected. The game is discontinued and nobody
		 * acquires any points.
		 */
		public static final String PLAYER_DISCONNECTED = "pc";
		
		/*
		 * =========== EXTENSION: CHATTING ===========
		 */
		
		/**
		 * This packet type is sent from client to server to send a message to other
		 * clients.
		 * If this packet is received on a client, it means another client sent them a
		 * message (this can also be <code>GLOBAL</code> or <code>LOBBY</code> messages
		 * of course).
		 */
		public static final String MESSAGE = "ms";
		
		/*
		 * =========== EXTENSION: CHALLENGING ===========
		 */
		
		/**
		 * This packet is a request from the client to the server to respond with a list
		 * of all the players that are connected. If the server responds with this packet
		 * type, it is the packet that contains the list.
		 */
		public static final String PLAYER_LIST = "pl";
		
		/**
		 * This packet type is sent from client to server containing the opponents the
		 * player wants to challenge.
		 * If this packet type is sent from the server to a client, it means that player
		 * was challenged and the packet will then contain the challenger's username.
		 */
		public static final String CHALLENGE = "cl";
		
		/**
		 * This is the reply packet from the client that has been challenged to the server
		 * (and indirectly the client that is challenging him/her).
		 */
		public static final String CHALLENGE_REPLY = "ch";
		
		/**
		 * This packet is sent from the server to the challenging client if any of the
		 * challenged players refused the proposal. Otherwise, the <code>GAME_STARTED</code>
		 * packet is sent to start the game.
		 * @see {@link #GAME_STARTED}
		 */
		public static final String CHALLENGE_REFUSED = "cr";
		
		/*
		 * =========== EXTENSION: LEADERBOARD ===========
		 */
		
		/**
		 * This packet type is sent from the client to the server to request a response packet
		 * containing the leaderboard of the server. If this packet type is sent from server
		 * to client it is the mentioned packet containing the server's leaderboard.
		 */
		public static final String LEADERBOARD = "lb";
		
		/**
		 * This packet type is sent from the client to the server to request a response packet
		 * containing the score log of a specific player. If this packet type is sent from server
		 * to client it is the mentioned packet containing the player's log of scores (with 
		 * scores and timestamps).
		 */
		public static final String SCORE_LOG = "sl";
		
		/*
		 * =========== EXTENSION: SECURITY ===========
		 */
		
		/**
		 * This packet type is sent from the client to the server to either log in or register
		 * (please refer to the document on the specific implementation of this). It is also sent
		 * from server to client to tell the client whether the login or register request was
		 * successful.
		 */
		public static final String LOGIN_REGISTER = "lr";
		
	}
}
