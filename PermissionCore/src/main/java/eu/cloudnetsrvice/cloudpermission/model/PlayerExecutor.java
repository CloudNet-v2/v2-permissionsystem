package eu.cloudnetsrvice.cloudpermission.model;

public class PlayerExecutor {
    protected boolean available = false;

    public PlayerExecutor() {
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void sendPlayer(CloudPlayer cloudPlayer, String server) {
    }

    public void kickPlayer(CloudPlayer cloudPlayer, String reason) {
    }

    public void sendMessage(CloudPlayer cloudPlayer, String message) {
    }

    public void sendActionbar(CloudPlayer cloudPlayer, String message) {
    }

    public void sendTitle(CloudPlayer cloudPlayer, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    }
}
