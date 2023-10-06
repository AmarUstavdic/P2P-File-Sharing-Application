import com.google.gson.Gson;

public class Msg {

    private MsgType msgType;

    public Msg(MsgType msgType) {
        this.msgType = msgType;

    }



    public String toJson() {
        return new Gson().toJson(this);
    }

}
