#  Networking protocol
###  Ringgz project group 2, 2017/2018
### Whatsapp group invite link: https://chat.whatsapp.com/CuvwqDJgAVB3YipAWILbVk

## Introductory

  This document will describe the protocol used by the Ringgz game implementation of programming group 2. It is supposed to be used as guideline for using the corresponding Java code. Any edge cases not specifically mentioned in this code should be appropriately handled (e.g.: when a player disconnects right after it requests a leaderboard, the leaderboard should not be sent back). If an edge case that is not mentioned in this document or in the documentation of the delivered code, and it needs a uniform way to handle it (as in: all servers should handle the error in the same way), please ask Koen van den Brink of programming group 2.
  
## Documentation syntax

  The documentation – in the code as well as this document – make use of a specific syntax. This is because packets sent from server to client must have a certain layout. In this layout, some arguments can be mandatory, and others can be optional to give. E.g.: when a game starts, the usernames of all the players are given. However, games can have different amounts of players. Therefore, if the usernames of all players that are playing are given, the amount of arguments can vary. Make sure this is handled in your client and server implementation!
  
  Mandatory packet information is documented with [square brackets] around it. Optional information (not actually optional, but not needed to conform to the networking protocol) is indicated with &lt;hooked brackets&gt;. This can be information that has to do with lists: it is needed, but clients and servers should handle packets that do and do not include that information. Information blocks in the documentation is separated with semicolons ( ; ). These semicolons are the agreed delimiter of information blocks and therefore should also seperate your information blocks in your implementation. The delimiter should not be at the start or end of a packet.
  
## Server default port

The server will make use of a default port that it will be listening on. This port will be port 23197.

## Usernames and chat messages

Usernames or chat messages can contain any character, excluding the semicolon ( ; ) character. Usernames and messages with special characters are allowed as long as long as the Java String object representation of these characters also does not contain the semicolon character. Notice that these special characters might appear different on other clients.

## Connecting

  The client starts with creating a connection with the server, using the server’s IP address and default port. It will then send a connect packet, of which the format is as follows:
  
### CONNECT;[username];&lt;extension1&gt;;&lt;extension2&gt;;&lt;extension3&gt;;&lt;extension4&gt; 

  Here, CONNECT is type of packet, username is the username your client will play as, and extension1, extension2, extension3 and extension4 are the extensions your client is using. 
These extensions can be the chatting-, challenging-, leaderboard- or security extension and should be handled by the server when given in any order. The same extension will not be given more than once. String representations of the extensions are found in the protocol code. If the extensions that are sent do not correspond to the string representations in the code, it can be ignored.

  The server will then send a response to this packet containing whether it is accepting the client:
  
### CONNECT;[accept/decline];&lt;extension1&gt;;&lt;extension2&gt;;&lt;extension3&gt;;&lt;extension4&gt;

  Here, CONNECT_REPLY is once again the type of packet. This will be assumed to be known from now on. In the format, accept/decline is whether the server accepted the connection of the client. The two string that will represent acceptance and declination will also be in the protocol code. The extension blocks are what extensions the server is using. Likewise, the client should handle these information blocks when given in any order and he same extension will not be given more than once.
  
## Request game

  A player can request a game and it will send the packet that has the following format:
  
### GAME_REQUEST;[player_amount];[player_type];&lt;opponent_type&gt;

  In the format, the player_amount represent the number of players that you want to play against.
  
  The player_type is the type of player that the client is running on.
  
  The opponent_type represents if you want to play versus a human or versus an AI. If the string that is sent does not correspond to what is found in the code, or opponent_type was not sent, the server must assume that the client has no preference and match the client with both humans and AI (whatever lobby the cliebt fits in first).
  
  The response from the server will be a textual feedback that lets the player know that he successfully joined in the lobby. 
  
  The format for this response is:

### JOINED_LOBBY

  Only the packet type needs to be sent, since this packet will always mean: “you are in the lobby and waiting for other players”.
  
## All players connected

  After all players connected to the lobby, the server will send a packet to the client side with the following format:
  
### ALL_PLAYERS_CONNECTED;[username1];[username2];&lt;username3&gt;;&lt;username4&gt;

  The order of the players doesn’t matter as long as the order in the packet corresponds with the order of players in the game. E.g.: username2 in this packet must correspond with the player 2 in the game.
  
  Notice that username1 will be the player placng the starting base.
  
  The response of the client side be a packet that let’s the server know if the player accepted or declined the beginning of the game.
  
### PLAYER_STATUS;[accept/decline]

If the accept/decline does not correspond to the ACCEPT or DECLINE in the code, DECLINE can be assumed by the server.

  If somebody declined, he or she will be removed from the lobby (he or she can now request a new game). All players whom accepted will be send the ”you are in and waiting” packet:
  
### JOINED_LOBBY

  If everybody accepted, the server will send the following packet:
  
### GAME_STARTED

  Notice the packet layout is the same as the “you are in and waiting” packet. However, the packet information block will differ and therefore, all the players will know that all other players accepted.

## Playing the game

  When the game starts, players need to know who starts. As said before: this will be the player with username1 in the ALL_PLAYERS_CONNECTED packet that was sent before.
  
### MAKE_MOVE

  The client will have to decide whether it is the first player (so it has to place the starting base)
  
  The response from the client will be the move that player made with the following format:
  
### MOVE;[x];[y];[move_type];&lt;color&gt;

  Here, X and Y represent the target coordinates of the piece that the player is placing (x horizontal and y vertical). Both of these coordinates range from 0-4. The move_type information block represents the type of piece the player is placing, these can be:
  
- Start base 
- Base
- Ring:
      - Smallest
      - Small
      - Normal
      - Huge

  The color information block represents what color a player is moving with. Notice that the color is not mandatory to conform to the protocol. If the move_type information block is representing the starting base, the color does not have to be given. Otherwise, it has to be given. The colors can be:
    - Primary
    - Secondary

  Move types and primary/secondary color string representations are found in the code. Colors are assigned on the client. The server always checks whether the move the player is making is valid. If it is not, the server resends the initial packet to the same client. Otherwise, the server send the following packet to all clients. Notice that not only x and y can be out of bounds, but also move_type and color can differ from the code.
  
### MOVE;[x];[y];[username];[move_type];&lt;color&gt;

All information in this packet is the same as in the previous packet. This will always be a valid move since the server only sends this packet if the move is valid. The only information block that was added was the username information block. This is the username of the player that made the move.

## Game Ended

  After the game has ended the server will send to the client a packet that lets us know how many points every player have.
  
### GAME_ENDED;[username1];[points1];[username2];[points2];&lt;username3&gt;;&lt;points3&gt;;&lt;username4&gt;;&lt;points4&gt;

  Here, all usernameK information blocks are linked to their respective score, given in scoreK. Clients can of course count the points themselves. However, the server will still send this information always when a game has ended. Therefore, a client will not have to count points when a game has ended. Notice that if the server supports the leaderboard extension, then the server must update the leaderboard.
  
## Player Disconnects

  If a player disconnects, every player will receive a message that let them know that have been moved to the request game state, explained previously.
  
### PLAYER_DISCONNECTED;[disconnected]

  Here, disconnecter is the username of the player that disconnected from the game.
As said, all remaining players move to the request game state, where they can request a new game. Nobody acquires any points.

## Chatting

  Chatting can be done easily. The client will only have to send the following packet:
  
### MESSAGE;[message_text];[message_type];&lt;receiver&gt;
  
  Here, message_text is the plain text message that the sender is sending, message_type is the type of message that the sender is sending. The string representations of these message types are in the protocol code. The types of messages are:
  
- Global: the message is sent to everyone that is connected to the server.
- Lobby: the message is sent to everyone that is currently in the same lobby (game) as the sender.
- Private: the message is sent to the player that is connected to the server and has the same username as given in the receiver information block. If the message_type does not correspond to the string representations in the code, GLOBAL can be assumed by the server. If the receiver username does not exist, the message is not sent to anybody.

All clients have to always be ready for the following packet from the server:

### MESSAGE;[message_text];[message_type];[sender]

  Here, message_text and message_type are again the message text and type, respectively (this is just the same as in the packet that the server received from the sender). However, the sender is the original sender of the message (with original sender is meant: the player that sent the message to the server).
  
## Challenging

  Before a player can challenge anyone, they must be able to request a list of all players currently connected to the server. This packet is formatted as follows:
  
## PLAYER_LIST

  The server will respond to this with a packet containing all the connected players:
  
### PLAYER_LIST;&lt;username1&gt;;&lt;username2&gt;;&lt;username3&gt;; … ;&lt;usernameN&gt;
  
  Here, usernameK is the username of the Kth player connected to the server.
  
  Now, the player has a list of players to select from. It can now challenge players by sending the following packet to the server:

### CHALLENGE;[opponent1];<opponent2>;<opponent3>

  In this packet, the opponent1, opponent2 and opponent3 information blocks are the usernames of the players that the requesting client is challenging. The second and third opponent are optional, since a player can choose to challenge one two or three players.
  
  The server will send the challenged players a packet that tells them that they have been challenged:
  
### CHALLENGE;[challenger]

  The challenger information block here is the username of the player that is challenging the packet receiving client. The receiving client will have to respond to this with the following packet:
  
### CHALLENGE_REPLY;[accept/decline]

  Here, accept/decline is whether the player accepted the challenge. If this response packet is not received within ten seconds ( ! ) on the server side or the server loses connection with this client, the server will function as if this client declined the request. Again: the variables that this packet uses for acceptance and declination are implemented in the given code.
  
  The server now has two different routes. In case every player accepted, it will send the same “game started” packet as when normally creating a game (see: “All players connected” in this document).
  
## GAME_STARTED

  Notice that GAME_STARTED here, will be the same string representation as if the game were to be made normally.
If one or more players refuse the challenge request, the server will send the challenger the following packet:

### CHALLENGE_REFUSED;&lt;refuser1&gt;;&lt;refuser2&gt;;&lt;refuser3&gt;

  Here, the different usernames of the players that refused the request are given in the refuser1, refuser2 and refuser3 information blocks.
  
  Note: as discussed, when a challenged client disconnects (or does not reply within time), the server will treat it as a refuse after ten seconds. However, if the challenger disconnects while his challenge is still pending, the server will send the other players a CHALLENGE_REFUSED packet, as if someone that was challenged had refused. All refuser blocks are left empty, which means that the challenger disconnect, ergo: refused the proposal him- or herself!
  
## Leaderboard

  For the leaderboard, the client can request a leaderboard with all total points of every player with the following packet:
  
### LEADERBOARD

  The server will then respond with a packet containing the usernames and total score of all players (that ever connected):
  
### LEADERBOARD;[player1];[score1];&lt;player2&gt;;&lt;score2&gt;; … &lt;playerN&gt;;&lt;scoreN&gt;
  
  Notice that every playerK username is linked with the scoreK information block right after it. Even if the score of a player is zero, the server must still send this.
  
The client can also choose to get a log of when a specific player acquired points, and how many. This is done by the client sending the following packet:

### SCORE_LOG;[username]

  Here, the username is the username of the player that the client wants to get a score log of. If the username does not exist, the server will not respond. The server will respond to this packet with the following packet:
  
### SCORE_LOG;&lt;score1&gt;;&lt;timestamp1&gt;;&lt;score2&gt;;&lt;timestamp2&gt;; … &lt;scoreN&gt;;&lt;timestampN&gt;
  
  Here, scoreK is linked to timestampK. This means that amount of points was acquired at that time. Notice all information blocks are optional. If a player has no points, and the client requests the log, the server will not have to return any score or timestamp.

## Security

  Security has to handle two operations: logging in and registering. Both are done by the client using the same packet format:
  
### LOGIN_REGISTER;[password];[login/register]

  Notice this packet does NOT contain the username of the player. The username was already given when the client first asked for the extensions of the server. A server with the security extension will (if the client also has the security extension) always accept the connection with the client, even if the username does not exist yet. The player will not be able to request any games until he or she has logged in. If the player is not logged in yet, he or she can register using the preceding packet. The password is the password that the server will directly store and login/register is whether the player wants to register the current username/password duo or if it wants to log in using the given username/password duo.
  
  The login/register information block is either the LOGIN or REGISTER string in the protocol code.
  
  Important: notice only a plan text password is given by the client, that is directly sent over the internet. Any salting and/or hashing and/or verifying password (like filling in a password twice to see if you did not make a typo) has to be done client side.
  
  The server will respond to the client’s packet with the following packet:
  
  ### LOGIN_REGISTER;[accept/decline]
  
  Note accept/decline use the same string representations as earlier accept/decline information blocks. However they can have more meanings depending on what the client sent earlier:
  
  If the client sent login and the server sends back accept: the client is now logged in with the username it connected with.
  
  If the client sent login and the server sends back decline: the username the client connected with either does not exist in the server database yet or the given password was incorrect.
  
  If the client sent register and the server sends back accept: the given username and password have been succesfully registered and the client is now also logged in.

  If the client sent register and the server sends back decline: something went wrong while storing the username and password in the server’s database or the username is already taken, ergo: already stored in the database.
