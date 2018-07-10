package levelbuilder;
import java.util.List;

import animations.Sprite;
import gameadditions.Army;
import shapes.Block;

/**.
 * @author David Geda
 * LevelInformation interface
 * an interface for all levels
 */
public interface LevelInformation {

          /**.
       * a getter for the wanted speed of the ship
       * @return double , the wanted ship speed
       */
   double getSpaceshipSpeed();
          /**.
       * a getter for the wanted width of the ship
       * @return int , the width of the ship
       */
   int getSpaceshipWidth();
       /**.
   * a getter the name of the level
   * @return String , a level name
   */
   String getLevelName();
          /**.
       * a getter for the background of this game
       * @return Sprite , a new background
       */
   Sprite getBackground();
   /**.
   * a getter for the aliens to be added to the game
   * @return List , a list of aliens
   */
   Army getArmy();
          /**.
       * a getter for the height of the game level
       * @return int , the height of the level
       */
   int getHeight();

    /**.
   * a getter for the width of the game level
   * @return int , the width of the level
   */
   int getWidth();
   
   /**.
  * a getter for the shields
  * @return List<Block> , the shields
  */
   List<Block> getShields();
}