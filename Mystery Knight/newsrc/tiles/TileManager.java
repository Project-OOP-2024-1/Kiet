package tiles;

import main.GameSetting;
import processors.SpriteSheet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GameSetting gs;
    public Tile[] tile;
    public int[][] mapTileNum1;
    public int[][] mapTileNum2;

    public TileManager(GameSetting gs) {

        this.gs = gs;
        tile = new Tile[600];
        mapTileNum1 = new int[gs.maxWorldCol][gs.maxWorldRow];
        mapTileNum2=  new int[gs.maxWorldCol][gs.maxWorldRow];
        getTileImage();
        mapTileNum2=loadMap("/maps/worldmap.txt");
        mapTileNum1=loadMap("/maps/worldmap2.txt");

    }

    public void getTileImage() {
        SpriteSheet sheet = new SpriteSheet("/tiles/map_tileset.png", gs.originalTileSize, gs.originalTileSize);
        for (int i =0; i< 18;i++){
            for (int j=0; j<29;j++){
                tile[i*29+j]=new Tile();
                tile[i*29+j].image = sheet.getSprite(j, i);
                tile[i*29+j].collision = false;
            }
        }
        //Set up for tile not collision
        int[] list = {203,204,232,233,261,262,290,291,319,320,348,349,343,372,373,417,418,
                419,420,446,447,448,449,475,476,477,478,504,505,506,507,458,459,460,461,209,
                210,211,212,238,241,265,266,267,270,271,272,294 ,301,323,330,352,353,354,357,
                358,359,383,386,412,413,414,415,155};
        for(int t : list){
            tile[t].collision=true;
        }

    }
    public int[][] loadMap(String filePath) {
        int[][] map= new int[gs.maxWorldCol][gs.maxWorldRow];
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gs.maxWorldCol && row < gs.maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                while (col < gs.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    map[col][row] = num;
                    col++;
                }
                if (col == gs.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gs.maxWorldCol && worldRow < gs.maxWorldRow) {
            int tileNum1 = mapTileNum1[worldCol][worldRow];
            int tileNum2 = mapTileNum2[worldCol][worldRow];


            int worldX = worldCol * gs.tileSize;
            int worldY = worldRow * gs.tileSize;
            int screenX = worldX - gs.player.x + gs.player.screenX;
            int screenY = worldY - gs.player.y + gs.player.screenY;

            if (worldX + gs.tileSize > gs.player.x - gs.player.screenX &&
                    worldX - gs.tileSize < gs.player.x + gs.player.screenX &&
                    worldY + gs.tileSize > gs.player.y - gs.player.screenY &&
                    worldY - gs.tileSize < gs.player.y + gs.player.screenY) {

                g2.drawImage(tile[tileNum2].image, screenX, screenY, gs.tileSize, gs.tileSize, null);
                g2.drawImage(tile[tileNum1].image, screenX, screenY, gs.tileSize, gs.tileSize, null);

            }


            worldCol++;


            if (worldCol == gs.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }

    }
}