package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RoomClass {
    private ArrayList points = new ArrayList();

    //put this in method to get even distribution

    private WeightedQuickUnionUF exit;

    private int numRooms = 0;
    private int xDoor;
    private int yDoor;
    private HashMap rooms = new HashMap<>();

    private TETile[][] board;

    /*
    @param: w = width of Room
    @param h = height of Room
    Constructor creates a new Room
     */

    public class Room {
        int width;
        int height;
        int centerX;
        int centerY;
        int x;
        int y;

        public Room(int w, int h, int xX, int yY) {
            this.width = w;
            this.height = h;
            this.x = xX;
            this.y = yY;
            this.centerX =  xX + (w / 2);
            this.centerY = yY + (h / 2);
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int[] center() {
            int[] cent = new int[2];
            cent[0] = centerX;
            cent[1] = centerY;
            return cent;
        }
    }


    public void addRoom(TETile[][] world, int worldWidth, int worldHeight,
                        int widthDivisor, int heightDivisior, Random random) {
        Room newRoom = generateRoom(worldWidth, worldHeight, widthDivisor, heightDivisior, random);
        makeRoom(world, newRoom);
    }

    private Room generateRoom(int upperBoundWidth, int upperBoundHeight,
                              int widthDivisor, int heightDivisor, Random random) {
        Position newLoc = generateXY(upperBoundWidth, upperBoundHeight,
                widthDivisor, heightDivisor, random);
        if (!points.contains(newLoc)) {
            points.add(newLoc);
            int x = newLoc.getX();
            int y = newLoc.getY();
            int width = random.nextInt(upperBoundWidth / widthDivisor);
            int height = random.nextInt(upperBoundHeight / heightDivisor);

            if (width < 6) {
                width = 6;
            }
            if (height < 6) {
                height = 6;
            }
            return new Room(width, height, x, y);
        }

        //int width = random.nextInt((upperBoundWidth - 4 - x) + 2);
        //int height = random.nextInt((upperBoundHeight - 4 - y) + 2);
        return generateRoom(upperBoundWidth, upperBoundHeight, widthDivisor, heightDivisor, random);
    }

    private void makeRoom(TETile[][] world, Room insertR) {
        addHorizontalWalls(world, insertR, false);
        addVerticalWalls(world, insertR, false);
        fillRoomCenter(world, insertR);
        rooms.put(numRooms, insertR);
        numRooms++;
    }

    private void connectRooms(TETile[][] world) {
        for (int i = 0; i < numRooms - 1; i++) {
            addHorizHallway(world, (Room) rooms.get(i), (Room) rooms.get(i + 1));
            addVertHallway(world, (Room) rooms.get(i), (Room) rooms.get(i + 1));

            // Checks to make sure corners got converted to walls
            if (world[((Room) rooms.get(i)).centerX]
                    [((Room) rooms.get(i + 1)).centerY] == Tileset.NOTHING) {
                world[((Room) rooms.get(i)).centerX]
                        [((Room) rooms.get(i + 1)).centerY] = Tileset.WALL;
            }

            if (world[((Room) rooms.get(i)).centerX + 1]
                    [((Room) rooms.get(i + 1)).centerY + 1] == Tileset.NOTHING) {
                world[((Room) rooms.get(i)).centerX + 1]
                        [((Room) rooms.get(i + 1)).centerY + 1] = Tileset.WALL;
            }

            if (world[((Room) rooms.get(i)).centerX - 1]
                    [((Room) rooms.get(i + 1)).centerY - 1] == Tileset.NOTHING) {
                world[((Room) rooms.get(i)).centerX - 1]
                        [((Room) rooms.get(i + 1)).centerY - 1] = Tileset.WALL;
            }

            if (world[((Room) rooms.get(i)).centerX - 1]
                    [((Room) rooms.get(i + 1)).centerY + 1] == Tileset.NOTHING) {
                world[((Room) rooms.get(i)).centerX - 1]
                        [((Room) rooms.get(i + 1)).centerY + 1] = Tileset.WALL;
            }

            if (world[((Room) rooms.get(i)).centerX + 1]
                    [((Room) rooms.get(i + 1)).centerY - 1] == Tileset.NOTHING) {
                world[((Room) rooms.get(i)).centerX + 1]
                        [((Room) rooms.get(i + 1)).centerY - 1] = Tileset.WALL;
            }
        }
    }

    private void addHorizHallway(TETile[][] world, Room room1, Room room2) {
        int xr1 = room1.center()[0];
        int xr2 = room2.center()[0];

        if (xr2 > xr1) {
            for (int i = xr1; i < xr2 + 1; i++) {
                world[i][room2.centerY] = Tileset.FLOOR;
                if (world[i][room2.centerY + 1] == Tileset.NOTHING) {
                    world[i][room2.centerY + 1] = Tileset.WALL;
                }
                if (world[i][room2.centerY - 1] == Tileset.NOTHING) {
                    world[i][room2.centerY - 1] = Tileset.WALL;
                }
            }
        } else {
            for (int i = xr2; i < xr1 + 1; i++) {
                world[i][room2.centerY] = Tileset.FLOOR;
                if (world[i][room2.centerY + 1] == Tileset.NOTHING) {
                    world[i][room2.centerY + 1] = Tileset.WALL;
                }
                if (world[i][room2.centerY - 1] == Tileset.NOTHING) {
                    world[i][room2.centerY - 1] = Tileset.WALL;
                }
            }
        }
    }

    private void addVertHallway(TETile[][] world, Room room1, Room room2) {
        int yr1 = room1.center()[1];
        int yr2 = room2.center()[1];

        if (yr2 > yr1) {
            for (int i = yr1; i < yr2 + 1; i++) {
                world[room1.centerX][i] = Tileset.FLOOR;
                if (world[room1.centerX + 1][i] == Tileset.NOTHING) {
                    world[room1.centerX + 1][i] = Tileset.WALL;
                }
                if (world[room1.centerX - 1][i] == Tileset.NOTHING) {
                    world[room1.centerX - 1][i] = Tileset.WALL;
                }
            }
        } else {
            for (int i = yr2; i < yr1 + 1; i++) {
                world[room1.centerX][i] = Tileset.FLOOR;
                if (world[room1.centerX + 1][i] == Tileset.NOTHING) {
                    world[room1.centerX + 1][i] = Tileset.WALL;
                }
                if (world[room1.centerX - 1][i] == Tileset.NOTHING) {
                    world[room1.centerX - 1][i] = Tileset.WALL;
                }
            }
        }
    }

    private void addDoor(TETile[][] world) {
        Room first = (Room) rooms.get(0);
        world[first.centerX][first.y] = Tileset.UNLOCKED_DOOR;
    }

    private void addHorizontalWalls(TETile[][] world, Room insertR, boolean isHallWay) {
        TETile tileToUse = Tileset.WALL;
        for (int i = 0; i < insertR.getWidth(); i++) {
            int x = insertR.getX();
            int y = insertR.getY();
            int h = insertR.getHeight();
            int x0 = i + x;
            int y0 = y;

            if (isHallWay) {
                tileToUse = intersect(x0, y0, world);
            }

            if (world[x0][y0] == Tileset.NOTHING) {
                world[x0][y0] = tileToUse;
            }

            int x1 = i + x;
            int y1 = y + h - 1;

            if (isHallWay) {
                tileToUse = intersect(x1, y1, world);
            }

            if (world[x1][y1] == Tileset.NOTHING) {
                world[x1][y1] = tileToUse;
            }


            points.add(new Position(x0, y0));
            points.add(new Position(x1, y1));
        }
    }

    private void addVerticalWalls(TETile[][] world, Room insertR, boolean isHallWay) {
        TETile tileToUse = Tileset.WALL;
        for (int i = 1; i < insertR.getHeight() - 1; i++) {
            int x = insertR.getX();
            int y = insertR.getY();
            int w = insertR.getWidth();
            //first wall
            int x0 = x;
            int y0 = i + y;
            if (isHallWay) {
                tileToUse = intersect(x0, y0, world);
            }

            if (world[x0][y0] == Tileset.NOTHING) {
                world[x0][y0] = tileToUse;
            }

            //second wall
            int x1 = x + w - 1;
            int y1 = i + y;
            if (isHallWay) {
                tileToUse = intersect(x1, y1, world);
            }

            if (world[x1][y1] == Tileset.NOTHING) {
                world[x1][y1] = tileToUse;
            }

            points.add(new Position(x0, y0));
            points.add(new Position(x1, y1));
        }
    }

//Fills rooms center with floor;
    public void fillRoomCenter(TETile[][] world, Room room) {
        int x = room.getX();
        int y = room.getY();
        int w = room.getWidth();
        int h = room.getHeight();
        for (int i = 1; i < h - 1; i++) {
            for (int j = 1; j < w - 1; j++) {
                world[x + j][y + i] = Tileset.FLOOR;
            }
        }
    }

//generates x y coordinates for a room or a hall way
    private Position generateXY(int width, int height,
                                int widthDivisor, int heightDivisor, Random random) {
        int x = (width / widthDivisor) * random.nextInt(widthDivisor);
        int y = (height / heightDivisor) * random.nextInt(heightDivisor);

        if (x >= width - 3) {
            x = width - 3;
        }
        if (y >= height - 3) {
            y = height - 3;
        }
        return new Position(x, y);
    }

    private TETile intersect(int x, int y, TETile[][] world) {
        if (world[x][y] == Tileset.FLOOR) {
            return Tileset.FLOOR;
        } else {
            return Tileset.WALL;
        }
    }

    private static class Position {
        int x;
        int y;

        private Position(int xX, int yY) {
            this.x = xX;
            this.y = yY;
        }

        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

    }



    public HashMap getRooms() {
        return rooms;
    }
    public int getxDoor() {
        return xDoor;
    }
    public int getyDoor() {
        return yDoor;
    }



    public void putUnlockedDoor(RoomClass rooming, Random random, TETile[][] world) {
        HashMap myRooms = rooming.getRooms();
        Room randRoom = (Room) myRooms.get(random.nextInt(rooming.numRooms));
        int x = randRoom.getX() + randRoom.getWidth() / 2;
        int y = randRoom.getY();
        if (world[x][y] == Tileset.WALL) {
            world[x][y] = Tileset.LOCKED_DOOR;
        } else {
            putUnlockedDoor(rooming, random, world);
        }
        xDoor = x;
        yDoor = y;
    }

    public void addAvatar(RoomClass rooming, Random random, HeroVsVillain avatarWorld) {
        HashMap myRooms = rooming.getRooms();
        int randRoomInt = random.nextInt(rooming.numRooms);
        Room randRoom = (Room) myRooms.get(randRoomInt);
        int x = randRoom.getX() + randRoom.getWidth() / 2;
        int y = randRoom.getY() + randRoom.getHeight() / 2;
        avatarWorld.avatarRoom = randRoomInt;
        avatarWorld.insertHero(x, y);
    }

    public void addVillains(RoomClass rooming, Random random, HeroVsVillain avatarWorld) {
        HashMap myRooms = rooming.getRooms();
        for (int j = 0; j < 2; j++) {
            for (int i = 1; i < myRooms.size(); i++) {
                if (i != avatarWorld.avatarRoom) {
                    Room randRoom = (Room) myRooms.get(i);
                    int x = randRoom.getX() + randRoom.getWidth() / 3;
                    int y = randRoom.getY() + randRoom.getHeight() / 3;
                    avatarWorld.insertVillain(x, y);
                }
            }
        }
    }

    public TETile[][] start(Long seed, RoomClass room, int w, int h) {

        int worldWidth = w;
        int worldHeight = h;

        TETile[][] world = new TETile[worldWidth][worldHeight];

        for (int x = 0; x < worldWidth; x += 1) {
            for (int y = 0; y < worldHeight; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        Random random = new Random(seed);

        int widthDivisor = random.nextInt(5) + 1;
        int heightDivisor = random.nextInt(5) + 1;

        RoomClass rooming = room;
        for (int i = 0; i < 6; i++) {
            rooming.addRoom(world, worldWidth - 5, worldHeight - 5, widthDivisor,
                    heightDivisor, random);
        }

        rooming.connectRooms(world);
        rooming.board = world;



        return world;
    }
}
