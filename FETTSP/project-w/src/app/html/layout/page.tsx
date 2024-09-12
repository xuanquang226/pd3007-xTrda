'use client';
import { relative } from "path";
import styles from "./layout.module.css"
import { useEffect, useState } from "react";
import { Gallery } from "react-grid-gallery";
import HandleImage from "../../../components/handle-img"


export default function LearnHTMLLayout() {
    const images = [
        "../images/apple.jpg",
        "../images/01.jpg",
        "../images/02.jpg",
        "../images/03.jpg",
        "../images/04.jpg",
        "../images/05.jpg",
        "../images/06.jpg",
        "../images/kitchen.jpg",
    ];
    const [listImage, setListImage] = useState<[]>([]);
    useEffect(() => {

        const listItem = [];

        for (let i = 0; i < images.length; i++) {
            listItem[i] = document.getElementById(`item${i + 1}`)
        }
        listItem.forEach(function (item) {
            if (item) {
                // setListImage(Array.from(cell.getElementsByTagName('img')) as []);
                Array.from(item.getElementsByTagName('img')).forEach(function (image) {
                    image.style.width = "100%";
                    image.style.height = "100%";
                    image.style.objectFit = "cover";
                });
            }
        });
        // listImage.forEach(function (img) {
        //     let image = img as HTMLImageElement;
        //     image.style.width = "100%";
        //     image.style.height = "auto";
        //     image.style.objectFit = "cover";
        // });
    }, []);


    const images2 = [
        {
            src: "../images/apple.jpg",
            width: 300,
            height: 600,
        },

        {
            src: "../images/01.jpg",
            width: 700,
            height: 600,
        },
        {
            src: "../images/02.jpg",
            width: 700,
            height: 200,

        },
        {
            src: "../images/03.jpg",
            width: 700,
            height: 500,

        },

        {
            src: "../images/kitchen.jpg",
            width: 700,
            height: 200,
        }
    ];


    return (
        <>
            {/* <Gallery
                images={images2} enableImageSelection={false}
            ></Gallery> */}

            <div className={styles['container-flexbox']}>
                {images.map((src, index) => (
                    <div className={styles["item"]} id={`item${index + 1}`}>
                        <img src={src} alt={`Image ${index + 1}`} />
                    </div>
                ))}
            </div>


            <div className="wrapper">
                <div className="box border border-info border-4 rounded rounded-2" style={{
                    width: "400px",
                    height: "250px",
                    position: "relative",
                    //top: "90px",
                    right: 0,
                    left: 0,
                    margin: "auto",
                    //transform: "translateY(-20%)",
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "space-between",
                    alignContent: "center"
                }}>
                    <div className={styles["box__box1"]} style={{
                        minHeight: "70px", backgroundColor: "green",
                        display: "flex", flexDirection: "row",
                        position: "relative", left: "200px",
                        transition: "transform 5s ease"
                    }}>


                    </div>
                    <div className="box__box2" style={{ minHeight: "70px", backgroundColor: "#808080", display: "flex", flexDirection: "column", position: "relative", left: "500px", alignItems: "center" }}>
                        <p>Content of <span style={{ color: "white", fontFamily: "sans-serif", fontSize: "40px", fontStyle: "italic" }}>row 1</span></p>
                        <p>Content of row 2</p>
                        <p>Content of row 3</p>
                    </div>
                    <div className="box__box3" style={{ minHeight: "70px", backgroundColor: "rgba(0, 128, 128, 20%)" }}>
                        <button className="box__box3__btnCancel">Cancel</button>
                    </div>
                </div>
                {/**
             * Card
             */}
                <div className="card-container">
                    <div className="card">
                        <div className="card-header">
                            <h6>Header</h6>
                        </div>
                        <div className="card-body">
                            <div className="card-title">
                                <h3>This is card title</h3>
                            </div>
                            <div className="card-subtitle">
                                <h5>This is card subtitle</h5>
                            </div>
                            <div className="card-text">
                                <p>This is content of card</p>
                            </div>
                        </div>
                        <div className="card-footer">
                            <h6>Footer</h6>
                        </div>
                    </div>
                </div>
                <div>
                    <select name="major">
                        <option value="CNTT">CNTT</option>
                        <option value="Điện tử">Điện tử</option>
                        <option value="Cơ khí">Cơ khí</option>
                    </select>
                </div>
                <div className="paragraph">
                    <h3>1. Văn bản</h3>
                    <p>Đoạn văn bản in <i>nghiêng</i></p>
                    <p>Đoạn văn bản in <b>đậm</b></p>
                    <p>Đoạn văn bản <small>nhỏ</small></p>
                    <p>Đoạn văn bản <big>lớn</big></p>
                    <p>Đoạn văn bản <mark>đánh dấu</mark></p>
                    <p>Đoạn văn bản bị <del>xóa</del></p>
                    <p>Đoạn <sub>subscript</sub></p>
                    <p>Đoạn <ins>insert</ins></p>
                </div>
                <hr></hr>
                <div className="list">
                    <h3>2. List</h3>
                    <div className="list__un-order-list">
                        <h3>Unorder list - Nguyên liệu canh đậu hủ trắng thịt xay</h3>
                        <ul>
                            <li>Đậu hủ trắng - 4 miếng</li>
                            <li>Hẹ - 1 bó</li>
                            <li>Thịt xay - 300g</li>
                            <li>Muối - 1/2 muỗng</li>
                            <li>Hạt nêm - 2.5 muỗng</li>
                            <li>Bột ngọt - 1/2 muỗng</li>
                            <li>Nước - 1.8l</li>
                        </ul>
                    </div>
                    <div className="list__order-list">
                        <h3>Order list - Các bước thực hiện</h3>
                        <ol type="1">
                            <li>
                                Sơ chế nguyên liệu
                                <ol type="A">
                                    <li>Xào thịt</li>
                                    <li>Cut đậu hủ</li>
                                    <li>Lặt hẹ</li>
                                </ol>
                            </li>
                            <li>Cho các nguyên liệu vào nồi</li>
                            <li>Nêm nếm</li>
                        </ol>
                    </div>
                    <div className="list__description-list">
                        <h3>Description list</h3>
                        <dl>
                            <dl>Bún bò</dl>
                            <dt>Đậm đà, bùng nổ vị giác</dt>
                            <dl>Phở</dl>
                            <dt>Thanh thanh, hương thơm nhẹ nhàng </dt>
                        </dl>
                    </div>
                </div>
            </div>
            <HandleImage ></HandleImage>
        </>
    );
}