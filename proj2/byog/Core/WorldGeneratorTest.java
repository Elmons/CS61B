package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
public class WorldGeneratorTest {
    private static final int WIDTH = 80, HEIGHT = 60;
    public static void main(String[] args) {
        Game game = new Game();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world;
//        world = game.playWithInputString("N254234345S");
//        ter.renderFrame(world);
        game.playWithKeyboard();
    }
}
