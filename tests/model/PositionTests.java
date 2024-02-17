package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods in the Position class for correct
 * behavior.
 */
public class PositionTests {
  Position pos1;
  Position pos2; //would be invalid in ReversiModel since coords do not add up to 0

  @Before
  public void setUp() {
    pos1 = new Position(-1,1,0);
    pos2 = new Position(25,-13, 30);
  }

  @Test
  //testing toString outputs the correct string
  public void testToStringWorksCorrectlyForPos1() {
    Assert.assertEquals("Position[q=-1, r=1, s=0]", pos1.toString());
  }

  @Test
  //testing toString outputs the correct string
  public void testToStringWorksCorrectlyForPos2() {
    Assert.assertEquals("Position[q=25, r=-13, s=30]", pos2.toString());
  }

  @Test
  //testing equals is false since none of pos1 coordinates equal pos2 coordinates
  public void testEqualsIsFalseSincePos1CoordsDoNotEqualPos2Coords() {
    Assert.assertFalse(pos1.equals(pos2));
  }

  @Test
  //testing equals is true since the coordinates of both positions are equal
  public void testEqualsIsTrueSincePosCoordsAreTheSame() {
    Position pos1SameCoords = new Position(-1,1,0);
    Assert.assertTrue(pos1.equals(pos1SameCoords));
  }

  @Test
  //testing equals is true since comparing pos1 to a copy of itself
  public void testEqualsIsTrueSincePosCoordsAreTheSameWithACopyOfItself() {
    Position pos1Copy = pos1;
    Assert.assertTrue(pos1.equals(pos1Copy));
  }

  @Test
  //testing hashCode outputs an int for pos1
  public void testHashCodeBehavesCorrectlyForPos1() {
    Assert.assertEquals(28861, pos1.hashCode());
  }

  @Test
  //testing hashCode outputs an int for pos2
  public void testHashCodeBehavesCorrectlyForPos2() {
    Assert.assertEquals(53443, pos2.hashCode());
  }

  @Test
  //testing hash codes for pos1 and pos2 are not the same
  public void testHashCodeIsDifferentForPos1AndPos2() {
    Assert.assertTrue(pos1.hashCode() != pos2.hashCode());
  }
}
