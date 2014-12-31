package com.jcloisterzone.ui.panel;

import static com.jcloisterzone.ui.I18nUtils._;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.jcloisterzone.Player;
import com.jcloisterzone.PointCategory;
import com.jcloisterzone.figure.SmallFollower;
import com.jcloisterzone.game.Game;
import com.jcloisterzone.ui.Client;
import com.jcloisterzone.ui.controls.ControlPanel;

public class GameOverPanel extends JPanel {

    private final Client client;
    private final Game game;

    public GameOverPanel(Client client, Game game) {
        this.client = client;
        this.game = game;

        //setTitle(_("Game overview"));

        setOpaque(false);
        setBackground(ControlPanel.PANEL_BG_COLOR);
        setLayout(new MigLayout("", "[]", "[][]10[]10[]20[][][][][]20[][]20[][][]"));
        int gridx = 0, gridy = 1;

        add(new JLabel(_("Player")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Rank")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Total points")), getLegendSpec(0, gridy++));

        add(new JLabel(_("Roads")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Cities")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Cloisters")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Farms")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Castles")), getLegendSpec(0, gridy++));

        add(new JLabel(_("The biggest city")), getLegendSpec(0, gridy++));
        add(new JLabel(_("The longest road")), getLegendSpec(0, gridy++));

        add(new JLabel(_("Trade goods")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Fairy")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Tower ransom")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Bazaars")), getLegendSpec(0, gridy++));
        add(new JLabel(_("Wind rose")), getLegendSpec(0, gridy++));

        Player[] players = getSortedPlayers().toArray(new Player[game.getAllPlayers().length]);
        for (Player player : players) {
            gridy = 0;
            Color color = player.getColors().getMeepleColor();
            Image img = client.getFigureTheme().getFigureImage(SmallFollower.class, color, null);
            Icon icon = new ImageIcon(img.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            add(new JLabel(icon, SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel(player.getNick(), SwingConstants.CENTER), getSpec(gridx, gridy++));

            add(new JLabel(getRank(players, gridx), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPoints(), SwingConstants.CENTER), getSpec(gridx, gridy++));

            add(new JLabel("" +player.getPointsInCategory(PointCategory.ROAD), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.CITY), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.CLOISTER), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.FARM), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.CASTLE), SwingConstants.CENTER), getSpec(gridx, gridy++));

            add(new JLabel("" +player.getPointsInCategory(PointCategory.BIGGEST_CITY), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.LONGEST_ROAD), SwingConstants.CENTER), getSpec(gridx, gridy++));

            add(new JLabel("" +player.getPointsInCategory(PointCategory.TRADE_GOODS), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.FAIRY), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.TOWER_RANSOM), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.BAZAAR_AUCTION), SwingConstants.CENTER), getSpec(gridx, gridy++));
            add(new JLabel("" +player.getPointsInCategory(PointCategory.WIND_ROSE), SwingConstants.CENTER), getSpec(gridx, gridy++));
            gridx++;
        }
    }

    private String getRank(Player[] players, int i) {
        int endrank = i+1;
        while(i > 0 && players[i-1].getPoints() == players[i].getPoints()) i--; //find start of group
        while(endrank < players.length) {
            if (players[endrank].getPoints() != players[i].getPoints()) break;
            endrank++;
        }
        if (endrank == i+1) {
            return "" + endrank;
        }
        return (i+1) + " - " + (endrank);
    }

    private String getLegendSpec(int x, int y) {
        return "cell " + x + " " + y + ", width 170::";
    }

    private String getSpec(int x, int y) {
        return "cell " + x + " " + y + ", width 120::, right";
    }

    private List<Player> getSortedPlayers() {
        List<Player> players = new ArrayList<>(Arrays.asList(game.getAllPlayers()));
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                if (o1.getPoints() < o2.getPoints()) return 1;
                if (o1.getPoints() > o2.getPoints()) return -1;
                return o1.getNick().compareToIgnoreCase(o2.getNick());
            }
        });
        return players;
    }

}
