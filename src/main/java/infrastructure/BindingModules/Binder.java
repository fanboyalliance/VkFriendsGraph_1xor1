package infrastructure.BindingModules;

import com.google.inject.AbstractModule;
import infrastructure.Requests.FriendGetter;
import infrastructure.Requests.HttpSenter;
import infrastructure.interfaces.IFriendGetter;
import infrastructure.interfaces.IHttpSenter;

public class Binder extends AbstractModule {

    @Override
    protected void configure() {
        bind(IFriendGetter.class).to(FriendGetter.class);
        bind(IHttpSenter.class).to(HttpSenter.class);
    }
}
