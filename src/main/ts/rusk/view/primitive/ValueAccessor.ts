module rusk {
    export module view {
        export module primitive {
            export interface ValueAccessor<T> {
                getValue() : T;
                setValue(value : T);
                clear();
            }
        }
    }
}
