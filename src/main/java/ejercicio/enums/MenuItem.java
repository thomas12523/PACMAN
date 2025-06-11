package ejercicio.enums;

public enum MenuItem {
    Exit,
    RUN_GAME,
    TOP_10_PACMAN;

    public static MenuItem getFromIndex(int index){
        if (index ==0){
            return Exit;
        }
        if (index == 1){
            return RUN_GAME;
        }
        if (index == 2){
            return TOP_10_PACMAN;
        }
        return null;
    }
}
