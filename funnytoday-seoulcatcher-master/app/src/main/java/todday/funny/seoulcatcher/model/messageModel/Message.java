package todday.funny.seoulcatcher.model.messageModel;

import todday.funny.seoulcatcher.model.Call;

public class Message {
    private String name;
    private String topic;
    private Call data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public Call getData() {
        return data;
    }

    public void setData(Call data) {
        this.data = data;
    }
}
