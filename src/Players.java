public class Players {
    private enum PlayerTurn {
        PLAYER_BLUE,
        PLAYER_RED
    }
    private Square playerSelected = null;
    private PlayerTurn currentPlayer = PlayerTurn.PLAYER_BLUE;
}
