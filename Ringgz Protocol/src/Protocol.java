public interface Protocol {
	
	/*
	 * =========== GENERAL ===========
	 */
	
	public static final String DELIMITER = ";";
	
	public static final String ACCEPT	= "0";
	public static final String DECLINE	= "1";
	
	/*
	 * =========== EXTENSION: CHATTING ===========
	 */
	
	public static final String GLOBAL	= "0";
	public static final String LOBBY	= "1";
	public static final String PRIVATE	= "2";
	
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
		 */
		public static final String CONNECT = "cn";
		
		/**
		 * This is the server's response to the client's CONNECT packet.
		 */
		public static final String CONNECT_REPLY = "cr";
		
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
		 * requests. If one or more players decline, they are thrown out of the lobby
		 * and the remaining players are re-sent a <code>JOINED_LOBBY</code> packet.
		 */
		public static final String GAME_STARTED = "gs";
		
		/*
		 * =========== PLAYING A GAME ===========
		 */
		
		/**
		 * This packet is sent at the very start of the game, so all players know who
		 * is the first player to move. Otherwise, clients would be unable to track
		 * whose turn it is for a couple turns.
		 */
		public static final String STARTING_PLAYER = "sp";
		
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
		
	}
}