import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bot1 {
    private Grid grid;
    private Fire fire;

    public Bot1(Grid grid, Fire fire) {
        this.grid = grid;
        this.fire = fire;
    }

    // • Bot 1 - This bot plans the shortest path to the button, avoiding the initial fire cell,
    // and then executes that
    // plan. The spread of the fire is ignored by the bot

    public List<Cell> finding_path_for_bot_one(Cell beginning, Cell ending)
    {
        // HashMap<Cell, Cell> remaking_the_path_for_the_cells = new HashMap<>();
        boolean[][] seen_these_cells = new boolean[grid.getRows()][grid.getCols()];
        Queue<Cell> lineup_of_the_cells = new LinkedList<>();

        seen_these_cells[beginning.getRow()][beginning.getCol()] = true; //changed
        beginning.setParent_of_the_cell(null);
        lineup_of_the_cells.add(beginning);
        // remaking_the_path_for_the_cells.put(beginning, null);

        while(!lineup_of_the_cells.isEmpty())
        {
            Cell the_present_cell = lineup_of_the_cells.remove();

            if(the_present_cell.equals(ending))
            {
                 return remaking_the_path_for_the_cells(ending);
            }
            
            //look thru the adjacent cells in the grid
            int[] indices_for_the_row = {-1, 1, 0, 0}; // changed
            int[] indices_for_the_colum = {0, 0, -1, 1}; // changed
            
           
            for(int i = 0 ; i < 4; i++)
            {
                int r = the_present_cell.getRow() + indices_for_the_row[i];
                int c = the_present_cell.getCol() + indices_for_the_colum[i];
                if(is_it_safe_to_visit_the_cel(r,c,seen_these_cells))
                {
                    seen_these_cells[r][c] = true;
                    
                    Cell look_at_upcoming_cell = grid.getCell(r,c);  
                   
                    look_at_upcoming_cell.setParent_of_the_cell(the_present_cell);     
                   
                    lineup_of_the_cells.add(look_at_upcoming_cell);         

                }
            }
        }
        return new LinkedList<>();  
    }

    private List<Cell> remaking_the_path_for_the_cells(Cell ending)
    {
        LinkedList<Cell> new_cell_path = new LinkedList<>();
        for (Cell i = ending; i != null; i = i.getParent_of_the_Cell()) 
        {
           // i.setPath();
            new_cell_path.addFirst(i);
        }
        return new_cell_path;
        
    }

    private boolean is_it_safe_to_visit_the_cel(int r, int c, boolean[][]seen_these_cells)
    {
        return r >= 0 &&
               r < grid.getCols()&& 
               c >= 0 && 
               c < grid.getRows() && 
               !seen_these_cells[r][c] && 
               !grid.getCell(r,c).hasInitialFire() &&
                grid.getCell(r,c).isOpen();
    }
  
    public void move_bot(List<Cell> path, Cell bot_cell, Cell button_cell, double ship_flambility) {
        for (int i = 0; i < path.size(); i++) {
            List<Cell> adj_open_cells = fire.get_all_adj_open_neigbors_of_fire_cells();
            fire.spread_fire(adj_open_cells, ship_flambility);
            Cell next_cell = path.get(i);
            bot_cell.setBot(false);
            next_cell.setBot(true);
            bot_cell = next_cell;
            grid.printGrid();
            if(bot_cell.hasFire()){
                System.out.println("Task failed");
                break;
            }
            System.out.println("**********");
            if (bot_cell.equals(button_cell)) {
                System.out.println("Task completed");
                fire.extinguish_fire();
                break;
            }
        }
        grid.printGrid();
     }



     


    
}
