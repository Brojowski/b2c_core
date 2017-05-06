package com.example.b2c_core;

import java.io.Serializable;
import java.util.*;
import java.util.List;

public class CityScorer implements Serializable
{
    private BuildingType[][] _city;

    private boolean[][] _visited = new boolean[4][4]; // inits to false
    boolean[][] _shops = new boolean[4][4];

    private int _numberOffices;
    private int _officeBonus;
    private int _numberFactories;
    private int _numberHouses;
    private int _factoryMultiplier;


    private static int[] OFFICE_MULTIPLIER = {1, 3, 6, 10, 15, 21};
    private static int[] PARK_MULTIPLIER = {2, 8, 12, 13, 14};
    private static int[] TAVERN_MULTIPLIER = {1, 4, 9, 17};
    private static int[] SHOP_MULTIPLIER = {2, 5, 10, 16};

    private ArrayList<EnumSet<BuildingType>> _tavernSets;
    private EnumSet<BuildingType> _modifierForHouses;

    private CityScorer(BuildingType[][] c, int pointsPerFactory)
    {
        _city = c;
        _factoryMultiplier = pointsPerFactory;
        _tavernSets = new ArrayList<>();
        _tavernSets.add(EnumSet.noneOf(BuildingType.class));
        _modifierForHouses = EnumSet.noneOf(BuildingType.class);
    }

    private int score()
    {
        int score = 0;
        for (int row = 0; row < _city.length; row++)
        {
            for (int col = 0; col < _city[row].length; col++)
            {
                if (!_visited[row][col])
                {
                    // Ideally this would be the last statement of the
                    // loop. However the continue statements cause it
                    //to be jumped.
                    _visited[row][col] = true;
                    markForHouses(_city[row][col]);
                    switch (_city[row][col])
                    {
                        case House:
                            if (tileRight(col, row) == BuildingType.Factory
                                    || tileLeft(col, row) == BuildingType.Factory
                                    || tileUp(col, row) == BuildingType.Factory
                                    || tileDown(col, row) == BuildingType.Factory)
                            {
                                // If a house is next to a factory it is worth 1pt.
                                score++;
                            }
                            else
                            {
                                // A house is only worth the modifier value if
                                // it is not next to a factory.
                                _numberHouses++;
                            }
                            continue;
                        case Factory:
                            _numberFactories++;
                            continue;
                        case Office:
                            _numberOffices++;
                            // Adds bonus if adjacent tile is a tavern.
                            bonusIfNearTavern(col, row);
                            continue;
                        case Shop:
                            markShop(col, row);
                            continue;
                        case Park:
                            // To start the recursion, we need to turn off the
                            // visited flag. See comment above for more info.
                            _visited[row][col] = false;
                            int parkGroupSize = scorePark(col, row);
                            if (parkGroupSize > PARK_MULTIPLIER.length)
                            {
                                parkGroupSize = PARK_MULTIPLIER.length;
                            }
                            score += PARK_MULTIPLIER[parkGroupSize - 1];
                            continue;
                        case Tavern_Bed:
                        case Tavern_Drink:
                        case Tavern_Food:
                        case Tavern_Music:
                            addTavernToSet(_city[row][col]);
                            continue;
                        case Blank:
                        default:
                    }
                }
            }
        }

        //System.out.println("Points from factories: " + _numberFactories * _factoryMultiplier);
        //System.out.println("Points from offices: " + scoreOffices());
        //System.out.println("Points from office bonuses: " + _officeBonus);
        //System.out.println("Points from taverns: " + scoreTavernSets());
        //System.out.println("Points from houses: " + _numberHouses * _modifierForHouses.size());
        //System.out.println("Points from parks: " + score);
        //System.out.println("Points from _shops: " + scoreShops(_shops.clone()));

        score += _numberFactories * _factoryMultiplier;
        score += scoreOffices();
        score += _officeBonus;
        score += scoreTavernSets();
        score += _numberHouses * _modifierForHouses.size();
        score += scoreShops(_shops.clone());

        return score;
    }

    private void bonusIfNearTavern(int x, int y)
    {
        if (isTavern(tileUp(x, y))
                || isTavern(tileDown(x, y))
                || isTavern(tileLeft(x, y))
                || isTavern(tileRight(x, y)))
        {
            _officeBonus++;
        }
    }

    private boolean isTavern(BuildingType type)
    {
        switch (type)
        {
            case Tavern_Music:
            case Tavern_Bed:
            case Tavern_Food:
            case Tavern_Drink:
                return true;
            default:
                return false;
        }
    }

    private void markForHouses(BuildingType buildingType)
    {
        switch (buildingType)
        {
            case Tavern_Bed:
            case Tavern_Food:
            case Tavern_Drink:
            case Tavern_Music:
                // Condensing all taverns down to Tavern_Bed
                // because the individual tavern types do not
                // matter when calculating the housing number.
                _modifierForHouses.add(BuildingType.Tavern_Bed);
                break;
            case Factory:
            case Shop:
            case Office:
            case Park:
                _modifierForHouses.add(buildingType);
                break;
            case Blank:
            case House:
            default:
                break;
        }
    }

    private int scoreTavernSets()
    {
        int tavernScore = 0;
        for (EnumSet<BuildingType> tavernSet : _tavernSets)
        {
            if (tavernSet.size() > 0)
            {
                tavernScore += TAVERN_MULTIPLIER[tavernSet.size() - 1];
            }
        }
        return tavernScore;
    }

    private void addTavernToSet(BuildingType buildingType)
    {
        boolean added = false;
        for (EnumSet<BuildingType> tavernSet : _tavernSets)
        {
            if (!tavernSet.contains(buildingType))
            {
                tavernSet.add(buildingType);
                added = true;
            }
        }
        if (!added)
        {
            EnumSet<BuildingType> newTavernSet = EnumSet.noneOf(BuildingType.class);
            newTavernSet.add(buildingType);
            _tavernSets.add(newTavernSet);
        }
    }

    private int scoreOffices()
    {
        if (_numberOffices == 0)
        {
            return 0;
        }

        int officeScore = 0;
        int workingNumberOffices = _numberOffices;
        while (workingNumberOffices > OFFICE_MULTIPLIER.length - 1)
        {
            officeScore += OFFICE_MULTIPLIER[OFFICE_MULTIPLIER.length - 1];
            workingNumberOffices -= OFFICE_MULTIPLIER.length;
        }
        officeScore += OFFICE_MULTIPLIER[workingNumberOffices - 1];
        return officeScore;
    }

    private int scorePark(int x, int y)
    {
        if (inBounds(x, y)
                && !_visited[y][x]
                && _city[y][x] == BuildingType.Park)
        {
            _visited[y][x] = true;
            return 1 + scorePark(x + 1, y)
                    + scorePark(x - 1, y)
                    + scorePark(x, y + 1)
                    + scorePark(x, y - 1);
        }
        return 0;

    }

    private void markShop(int x, int y)
    {
        _shops[y][x] = true;
    }

    private boolean[][] cloneBoolArr(boolean[][] input)
    {
        boolean[][] output = new boolean[input.length][];
        for (int y = 0; y < input.length; y++)
        {
            output[y] = new boolean[input[y].length];
            for (int x = 0; x < input[y].length; x++)
            {
                output[y][x] = input[y][x];
            }
        }
        return output;
    }

    private int scoreShops(boolean[][] shops)
    {
        List<List<Point>> adjShops = new ArrayList<>();
        boolean[][] vertShops = cloneBoolArr(shops);
        boolean[][] horizShops = cloneBoolArr(shops);
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                adjShops.add(shopsInARow(new ArrayList<Point>(), x, y, vertShops));
                adjShops.add(shopsInACol(new ArrayList<Point>(), x, y, horizShops));
            }
        }

        List<List<Point>> longestSets = new ArrayList<>();
        for (int length = 4; length > 0; length--)
        {
            for (List<Point> set : adjShops)
            {
                if (set.size() == length)
                {
                    longestSets.add(set);
                }
            }
            if (longestSets.size() > 0)
            {
                break;
            }
        }

        int highestValue = 0;
        for (List<Point> longSet : longestSets)
        {
            boolean[][] shopClone = cloneBoolArr(shops);
            int shopScore = SHOP_MULTIPLIER[longSet.size() - 1];
            for (Point p : longSet)
            {
                shopClone[p.y][p.x] = false;
            }
            shopScore += scoreShops(shopClone.clone());
            //System.out.println("Shop score: " + shopScore);
            highestValue = (shopScore > highestValue) ? shopScore : highestValue;
        }

        return highestValue;
    }

    private List<Point> shopsInARow(List<Point> row, int x, int y, boolean[][] shopsRow)
    {
        if (inBounds(x, y) && shopsRow[y][x])
        {
            shopsRow[y][x] = false;
            row.add(new Point(x, y));
            shopsInARow(row, x + 1, y, shopsRow);
        }
        return row;
    }

    private List<Point> shopsInACol(List<Point> col, int x, int y, boolean[][] shopsCol)
    {
        if (inBounds(x, y) && shopsCol[y][x])
        {
            shopsCol[y][x] = false;
            col.add(new Point(x, y));
            shopsInACol(col, x, y + 1, shopsCol);
        }
        return col;
    }

    private boolean inBounds(int x, int y)
    {
        return x >= 0
                && x < 4
                && y >= 0
                && y < 4;
    }

    public BuildingType tileUp(int x, int y)
    {
        int moddedIndex = y - 1;
        if (moddedIndex >= 0 && moddedIndex < 4)
        {
            return _city[moddedIndex][x];
        }
        else
        {
            return BuildingType.Blank;
        }
    }

    public BuildingType tileDown(int x, int y)
    {
        int moddedIndex = y + 1;
        if (moddedIndex >= 0 && moddedIndex < 4)
        {
            return _city[moddedIndex][x];
        }
        else
        {
            return BuildingType.Blank;
        }
    }

    public BuildingType tileLeft(int x, int y)
    {
        int moddedIndex = x - 1;
        if (moddedIndex >= 0 && moddedIndex < 4)
        {
            return _city[y][moddedIndex];
        }
        else
        {
            return BuildingType.Blank;
        }
    }

    public BuildingType tileRight(int x, int y)
    {
        int moddedIndex = x + 1;
        if (moddedIndex >= 0 && moddedIndex < 4)
        {
            return _city[y][moddedIndex];
        }
        else
        {
            return BuildingType.Blank;
        }
    }

    public static int scoreCity(City toScore, int pointsPerFactory)
    {
        CityScorer scorer = new CityScorer(toScore.getCityLayout(), pointsPerFactory);
        return scorer.score();
    }
}
