import CartItem from "./cart-item";
export default interface Cart{
    id: number;
    idCustomer: number;
    status: string;
    totalPrice: string;
    codeDiscount: string;
    notes: string;
    listCartItem: CartItem[];
}