import {create} from 'zustand';
import CartItem from '@/type/cart-item';

interface CartStore{
    cartItemStore: CartItem;
    addCartItem: (cartItem: CartItem) => void;
}

const useCartStore = create<CartStore>((set) => ({
    cartItemStore: {
        id: 0,
        idCart: 0,
        idProduct: 0,
        quantity: 0,
        price: null,
        note: null,
        name: null
    },

    addCartItem: (cartItem) => set(({cartItemStore:cartItem})),

    // addCartItem(cartItem) {
    //     set(({cartItemStore: cartItem}))
    // },
}));

export default useCartStore;