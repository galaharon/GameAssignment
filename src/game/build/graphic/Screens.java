package game.build.graphic;

import static game.build.util.Reference.BACK_BUTTON;
import static game.build.util.Reference.BUTTON;
import static game.build.util.Reference.CREATE_BUTTON;
import static game.build.util.Reference.ENDLESS_BUTTON;
import static game.build.util.Reference.EXIT_BUTTON;
import static game.build.util.Reference.GAME_DIMENSION;
import static game.build.util.Reference.INFO_BUTTON;
import static game.build.util.Reference.PLAY_BUTTON;
import static game.build.util.Reference.SONG_SELECT_BUTTON;

public class Screens
{
	public static MenuPane mainMenu()
	{
		MenuPane menuPane = new MenuPane("main_background");
		int first = 300;
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width -32 - 2*BUTTON.width)/2,first, PLAY_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width +32)/2,first, CREATE_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width -32 - 2*BUTTON.width)/2, first + BUTTON.height + 32, INFO_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width + 32)/2,first + BUTTON.height + 32, EXIT_BUTTON));
		return menuPane;
	}

	public static MenuPane levelCreation()
	{
		return null;
	}

	public static MenuPane infoScreen()
	{
		MenuPane menuPane = new MenuPane("info_screen");
		menuPane.addLayer(new ButtonPanel(32,GAME_DIMENSION.height - BUTTON.height - 64, BACK_BUTTON));
		return menuPane;
	}

	public static MenuPane playSelectScreen()
	{
		MenuPane menuPane = new MenuPane("main_background");
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width -16 - 2*BUTTON.width)/2,200, ENDLESS_BUTTON));
		menuPane.addLayer(new ButtonPanel((GAME_DIMENSION.width + 16)/2,200, SONG_SELECT_BUTTON));
		menuPane.addLayer(new ButtonPanel(32,GAME_DIMENSION.height - BUTTON.height - 64, BACK_BUTTON));
		return menuPane;
	}
	
	public static MenuPane endlessMode()
	{
		return new EndlessMode();
	}
}
