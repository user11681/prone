package user11681.prone.asm.access;

public interface ProneEntity {
    /**
     * @return whether this entity is actually swimming or not.
     */
    default boolean prone_isActuallySwimming() {
        return false;
    }

    default boolean prone_isProne() {
        return false;
    }

    default void prone_setProne(boolean prone) {

    }

    default void prone_toggleProne() {
        this.prone_setProne(!this.prone_isProne());
    }

    default void prone_sync() {}
}
