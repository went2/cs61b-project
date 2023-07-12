package byow.Core.dungeon;
import byow.Core.RandomUtils;
import byow.GeoPrimitives.Container;
import byow.GeoPrimitives.Corridor;
import byow.GeoPrimitives.Point;
import edu.princeton.cs.algs4.Stopwatch;
import byow.TileEngine.Tileset;
import byow.GeoPrimitives.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Dungeon extends Map {

    private HashSet<Rectangle> rooms = new HashSet<>();
    private TreeNode<Container> tree;
    private Random random;

    public Dungeon(DungeonArg args) {
        super(args.mapWidth, args.mapHeight);

        Stopwatch sw = new Stopwatch();
        generate(args);
        double time = sw.elapsedTime();
        System.out.println("Dungeon generated in " + time + "s");
    }

    public void generate(DungeonArg args) {
        random = new Random(args.seed);

        // fill(Tileset.WALL);
        tree = createMapTree(args);

    }

    // Recursively splitting map into 2 halves
    private TreeNode<Container> createMapTree(DungeonArg args) {
        Container wholeMap = new Container(
                args.mapGutterWidth,
                args.mapGutterWidth,
                args.mapWidth,
                args.mapHeight
        );
        TreeNode<Container> tree = generateTree(wholeMap, args.iterations, args);

        generateRooms(tree, args);
        return tree;
    }

    // recursive divide map into halves
    private TreeNode<Container> generateTree(Container container, int iterations, DungeonArg args) {
        TreeNode<Container> node = new TreeNode<>(container);

        // stop dive if run out of iterations or container size could not be divided
        if(
                iterations != 0 &&
                node.region.width > args.containerMinimumSize * 2 &&
                node.region.width > args.containerMinimumSize * 2
        ) {
            Container[] subContainers = splitContainer(container, args);
            Container left = subContainers[0];
            Container right = subContainers[1];
            if(left != null && right != null) {
                node.left = generateTree(left, iterations - 1, args);
                node.right = generateTree(right, iterations - 1, args);

                // Once divided, we create a corridor between the two containers
                node.region.corridor = generateCorridor(
                        node.left.region, node.right.region, args
                );
            }
        }
        return node;
    }

    private Container[] splitContainer(Container container, DungeonArg args) {
        Container left;
        Container right;

        // Generate a random direction to split the container, 1 vertical, 0 horizontal
        int direction = RandomUtils.uniform(random, 2);
        if(direction == 1) { // vertical
            left = new Container(container.x, container.y,RandomUtils.uniform(random, 2, container.width), container.height);
            right = new Container(container.x + left.width, container.y, container.width - left.width, container.height);
        } else { // horizontal
            left = new Container(container.x, container.y, container.width, RandomUtils.uniform(random, 2,container.height));
            right = new Container(container.x, container.y + left.height, container.width, container.height - left.height);
        }

        return (Container[]) new Object[] { left, right };
    }

    private Corridor generateCorridor(Container left, Container right, DungeonArg args) {
        // create Corridor
        Point<Integer> leftCenter = left.center();
        Point<Integer> rightCenter = right.center();
        int x = leftCenter.x;
        int y = leftCenter.y;
        Corridor corridor;
        if(leftCenter.x.equals(rightCenter.x)) {
            // vertically divided
            corridor = new Corridor(
                    x - args.corridorWidth / 2,
                    y - args.corridorWidth / 2,
                    args.corridorWidth,
                    rightCenter.y - leftCenter.y
            );
        } else {
            corridor = new Corridor(
                    x - args.corridorWidth / 2,
                    y - args.corridorWidth / 2,
                    rightCenter.x - leftCenter.x,
                    args.corridorWidth
            );
        }

        return corridor;
    }

    private void generateRooms(TreeNode<Container> tree, DungeonArg args) {

    }









}

