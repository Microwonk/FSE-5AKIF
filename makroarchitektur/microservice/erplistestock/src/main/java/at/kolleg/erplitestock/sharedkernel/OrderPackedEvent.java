package at.kolleg.erplitestock.sharedkernel;

import java.io.Serializable;

public record OrderPackedEvent(String orderId) implements Serializable
{

}