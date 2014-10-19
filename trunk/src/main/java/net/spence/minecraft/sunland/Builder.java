package net.spence.minecraft.sunland;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Builder
{
    public static void generateCube(Location loc, int length, Material material)
    {
        // Set one corner of the cube to the given location.
        // Uses getBlockN() instead of getN() to avoid casting to an int later.
        int x1 = loc.getBlockX();
        int y1 = loc.getBlockY();
        int z1 = loc.getBlockZ();

        // Figure out the opposite corner of the cube by taking the corner and
        // adding length to all coordinates.
        int x2 = x1 + length;
        int y2 = y1 + length;
        int z2 = z1 + length;

        World world = loc.getWorld();

        // Loop over the cube in the x dimension.
        for (int xPoint = x1; xPoint <= x2; xPoint++) {
            // Loop over the cube in the y dimension.
            for (int yPoint = y1; yPoint <= y2; yPoint++) {
                // Loop over the cube in the z dimension.
                for (int zPoint = z1; zPoint <= z2; zPoint++) {
                    // Get the block that we are currently looping over.
                    Block currentBlock = world.getBlockAt(xPoint, yPoint,
                            zPoint);
                    // Set the block to type 57 (Diamond block!)
                    currentBlock.setType(material);
                }
            }
        }
    }

    // Shortcut to build a large flat surface
    public static void BuildSurface(World w, int x1, int y1, int z1, int x2, int y2, int z2, Material m)
    {
        Block b = null;
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    b = w.getBlockAt(x, y, z);
                    b.setType(m);
                }
            }
        }
    }
    
    // Generate a maze with a specified size
    public static void generateMaze(Location loc, int size)
    {
        MazeGenerator maze = new MazeGenerator(size, size);
        int[][] mazearray = maze.getMaze();

        // Uses getBlockN() instead of getN() to avoid casting to an int later.
        int x1 = loc.getBlockX();
        int y1 = loc.getBlockY();
        int z1 = loc.getBlockZ();

        // The maze has blocks of size 5 in each direction - so multiply by 5 *
        // size and / 2
        int maze_dimension = (size * 5);
        int offset = (maze_dimension / 2);
        int xstart = x1 - offset;
        int zstart = z1 - offset;

        // Loop over the maze in the x and z dimensions, drawing blocks or not
        // blocks based on the contents
        World world = loc.getWorld();
        Block b = null;
        
        // Draw a flat surface for the maze to stand on
        BuildSurface(world, xstart, y1 - 1, zstart, xstart + maze_dimension, y1 - 1, zstart + maze_dimension, Material.SMOOTH_BRICK);
        
        // CLear out the interior of the maze - make it all air
        BuildSurface(world, xstart, y1, zstart, xstart + maze_dimension, y1 + 3, zstart + maze_dimension, Material.AIR);
        
        // Build the enclosing walls for the maze
        BuildSurface(world, xstart, y1, zstart, xstart + maze_dimension, y1 + 3, zstart, Material.SMOOTH_BRICK);
        BuildSurface(world, xstart, y1, zstart + maze_dimension, xstart + maze_dimension, y1 + 3, zstart + maze_dimension, Material.SMOOTH_BRICK);
        BuildSurface(world, xstart, y1, zstart, xstart, y1 + 3, zstart + maze_dimension, Material.SMOOTH_BRICK);
        BuildSurface(world, xstart + maze_dimension, y1, zstart, xstart + maze_dimension, y1 + 3, zstart + maze_dimension, Material.SMOOTH_BRICK);
        
        // Randomly pick which wall has the exit
        Random rand = new Random();
        int wall = rand.nextInt(4);
        int pos = rand.nextInt(size) + 1;

        // Pick the position along the wall and replace it with air
        if (wall == 0) {
            BuildSurface(world, xstart + (pos * 5), y1, zstart, xstart + (pos * 5) + 5, y1 + 3, zstart, Material.AIR);
        } else if (wall == 1) {
            BuildSurface(world, xstart + (pos * 5), y1, zstart + maze_dimension, xstart + (pos * 5) + 5, y1 + 3, zstart + maze_dimension, Material.AIR);
        } else if (wall == 2) {
            BuildSurface(world, xstart, y1, zstart + (pos * 5), xstart, y1 + 3, zstart + (pos * 5) + 5, Material.AIR);
        } else if (wall == 3) {
            BuildSurface(world, xstart + maze_dimension, y1, zstart + (pos * 5), xstart + maze_dimension, y1 + 3, zstart + (pos * 5) + 5, Material.AIR);
        }
        
        // Fill in the walls of the maze
        for (int y = y1; y < y1 + 4; y++) {
            int xpos = xstart;
            for (int x = 0; x < size; x++) {
                int zpos = zstart;
                for (int z = 0; z < size; z++) {

                    // Always build this corner block
                    b = world.getBlockAt(xpos, y, zpos);
                    b.setType(Material.SMOOTH_BRICK);

                    // Is there a x wall? If so, draw five blocks
                    if ((mazearray[x][z] & 1) == 0) {
                        b = world.getBlockAt(xpos + 1, y, zpos);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos + 2, y, zpos);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos + 3, y, zpos);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos + 4, y, zpos);
                        b.setType(Material.SMOOTH_BRICK);
                    }

                    // Is there a z wall?
                    if ((mazearray[x][z] & 8) == 0) {
                        b = world.getBlockAt(xpos, y, zpos);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos, y, zpos + 1);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos, y, zpos + 2);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos, y, zpos + 3);
                        b.setType(Material.SMOOTH_BRICK);
                        b = world.getBlockAt(xpos, y, zpos + 4);
                        b.setType(Material.SMOOTH_BRICK);
                    }
                    zpos += 5;
                }
                
                // Move to next line
                xpos += 5;
            }
        }
    }
}
