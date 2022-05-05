package com.link.pay.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_order")
public class Order {

    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 业务类型，可动态扩充。商品购买、会员充值、金币充值
     */
    private String bizType;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private Double price;

    /**
     * 总价
     */
    private Double totalAmount;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 优惠价格
     */
    private Double discount;

    /**
     * 二维码
     */
    private String qr;

    /**
     * 状态。0=待支付，1=已支付，2=已失效，3=已取消，4=待退款，5=已退款，6=已删除
     */
    private Integer status;

    /**
     * 支付到期时间
     */
    private Long payExpireTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

}
