package eu.cloudnetservice.cloudpermission.module.event;

import de.dytanic.cloudnet.event.async.AsyncEvent;
import de.dytanic.cloudnet.event.async.AsyncPosterAdapter;
import eu.cloudnetsrvice.cloudpermission.model.OfflinePlayer;

public class UpdatePlayerEvent extends AsyncEvent<UpdatePlayerEvent> {

    private OfflinePlayer offlinePlayer;

    public UpdatePlayerEvent(OfflinePlayer offlinePlayer) {
        super(new AsyncPosterAdapter<>());
        this.offlinePlayer = offlinePlayer;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }
}
