package com.ejb.socialnw.util;

import java.util.Comparator;

import com.ejb.socialnw.entity.message.Message;

public class MessageSorter implements Comparator<Message> {

    @Override
    public int compare(Message o1, Message o2) {
        return o1.getDate().compareTo(o2.getDate()) * (-1);

    }

}
