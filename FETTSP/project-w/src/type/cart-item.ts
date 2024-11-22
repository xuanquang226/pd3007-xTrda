export default interface CartItem{
    id: number;
    idCart: number;
    idProduct: number;
    quantity: number;
    price: string|null;
    note: string|null;
    name: string|null;
}