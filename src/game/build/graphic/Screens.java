package game.build.graphic;

import static game.build.util.Reference.BACK_BUTTON;
import static game.build.util.Reference.BUTTON;
import static game.build.util.Reference.CREATE_BUTTON;
import static game.build.util.Reference.ENDLESS_BUTTON;
import static game.build.util.Reference.ENDLESS_TRY_AGAIN;
import static game.build.util.Reference.EXIT_BUTTON;
import static game.build.util.Reference.GAME_DIMENSION;
import static game.build.util.Reference.INFO_BUTTON;
import static game.build.util.Reference.PLAY_BUTTON;
import static game.build.util.Reference.SONG_SELECT_BUTTON;
import game.build.main.SongPlayer;
import game.build.util.Resources;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Screens
{
	public static MenuPane mainMenu()
	{
		SongPlayer.loop.set(false);
		SongPlayer.playSong("main");
		SongPlayer.loop.set(true);
		MenuPane menuPane = new MenuPane("main_background"); //MAKE THE SCREEN
		int first = 300;
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width -32 - 2*BUTTON.width)/2,first, PLAY_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width +32)/2,first, CREATE_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width -32 - 2*BUTTON.width)/2, first + BUTTON.height + 32, INFO_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width + 32)/2,first + BUTTON.height + 32, EXIT_BUTTON));
		return menuPane;
	}

	public static MenuPane levelCreation()
	{
		JFileChooser chooser = new JFileChooser(Resources.songDir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Song files (*.mp3)", "mp3");
		chooser.setDialogTitle("Load a song");
		chooser.setFileFilter(filter);
		MenuPane pane = mainMenu();
		int returnVal = chooser.showOpenDialog(pane);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File song = chooser.getSelectedFile();
			SongPlayer.loop.set(false);
			return new LevelCreator(song);
		}
		return pane;
	}

	public static MenuPane infoScreen()
	{
		MenuPane menuPane = new MenuPane("info_screen");
		menuPane.addLayer(new ButtonPanel(32,GAME_DIMENSION.height - BUTTON.height - 64, BACK_BUTTON));
		return menuPane;
	}

	public static MenuPane modeSelect()
	{
		MenuPane menuPane = new MenuPane("main_background");
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width -16 - 2*BUTTON.width)/2,200, SONG_SELECT_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width + 16)/2,200, ENDLESS_BUTTON));
		menuPane.addLayer(new ButtonPanel(32,GAME_DIMENSION.height - BUTTON.height - 64, BACK_BUTTON));
		return menuPane;
	}
	
	public static MenuPane endlessMode()
	{
		SongPlayer.loop.set(false);
		SongPlayer.playSong("endless");
		SongPlayer.loop.set(true);
		return new EndlessMode();
	}

	public static MenuPane endlessOver(int min, int sec, int ms)
	{
		SongPlayer.loop.set(false);
		SongPlayer.playSong("loss");
		SongPlayer.loop.set(true);
		EndlessLoss pane = new EndlessLoss(formatTime(min,sec,ms));
		pane.addLayer(new ButtonPanel(GAME_DIMENSION.width - BUTTON.width - 32,GAME_DIMENSION.height - BUTTON.height - 64, ENDLESS_TRY_AGAIN));
		pane.addLayer(new ButtonPanel(32,GAME_DIMENSION.height - BUTTON.height - 64, BACK_BUTTON));
		return pane;
	}

	public static MenuPane songSelect()
	{
		JFileChooser chooser = new JFileChooser(Resources.mapDir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Beatmap file (*.map)", "map");
		chooser.setDialogTitle("Load a song");
		chooser.setFileFilter(filter);
		MenuPane pane = modeSelect();
		int returnVal = chooser.showOpenDialog(pane);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File song = chooser.getSelectedFile();
			System.out.println(song);
			SongPlayer.loop.set(false);
			NormalMode mode = new NormalMode(song);
			return mode;
		}
		return pane;
	}

	public static MenuPane normalOver(String songName, int min, int sec, int ms)
	{
		System.out.println("Lost " + songName +", survived for " + formatTime(min, sec, ms));
		return mainMenu();
	}

	public static MenuPane normalWon(String songName)
	{
		System.out.println("Won " + songName + "!");
		return mainMenu();
	}
	
	public static String formatTime(int min, int sec, int ms)
	{
		float f = sec + ms/1000F;
		
		return (min != 0 ? min == 1 ? "1 min " : min + " mins " : "") + String.format("%.3f secs",f);
	}
}
