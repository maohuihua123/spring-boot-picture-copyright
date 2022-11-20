pragma solidity ^0.5.0;

contract PictureCopyright {

    // 版权信息
    struct Copyright {
        uint pictureID;
        // 原文件的哈希值
        string pictureHash;
        // 水印信息
        string waterMark;
        // 加水印后图片地址(IPFS)
        string picturePath;
		// 图片所有者
        address pictureOwner;
        // 创建时间
        uint createTime;
    }

    // 用户信息
    struct User {
        // 以太坊地址
        address ethAddress;
        // 昵称
        string nickName;
        // 联系方式
        string email;
        // 创建时间
        uint createTime;
    }


    // 用户集合大小
	uint public userNumber;
	// 用户集合
	mapping(uint => User) public users;

	// 区块链存储的版权数量
	uint public copyrightNumber;
	// 版权集合
	mapping(uint => Copyright) public copyrights;


    // 某个用户的个人信息
    mapping(address => uint) userIndex;
	// 某个用户的版权数量
	mapping(address => uint) userCopyrightsNumber;
	// 某个用户的版权信息在所有版权信息的索引(即[address][index] = copyrightNumber)
	mapping(address => mapping(uint => uint)) userCopyrightsIndex;


    // 合约部署者
    address host;


    event setCopyrightEvent(address ethAddress,uint index);

    constructor() public {
        host = msg.sender;
    }

    // 创建用户 用户调用的接口
    function createUser(string memory name,string memory email) public {
        // 存储用户索引
        userIndex[msg.sender] = userNumber;
        // 存储用户信息，同时索引++
        users[userNumber++] = User(msg.sender, name, email, now);
    }
    // 通过获得个人索引 来获得个人信息 用户调用的接口
    function getUserIndex() public view returns(uint){
        // 获得用户自己的索引
        uint index = userIndex[msg.sender];
        return index;
    }

    // 录入图片版权信息 用户调用的接口
    function setCopyright(string memory picHash, string memory mark, string memory picturePath) public {
        // 某个用户的版权数量
        uint index =  userCopyrightsNumber[msg.sender];
        // 存储该用户版权信息的索引
        userCopyrightsIndex[msg.sender][index] = copyrightNumber;
        // 数量++
        userCopyrightsNumber[msg.sender]++;
        // 存储版权信息，同时索引++
        copyrights[copyrightNumber] = Copyright(copyrightNumber,picHash, mark, picturePath, msg.sender, now);
        // 数量加一
        copyrightNumber++;
        emit setCopyrightEvent(msg.sender, copyrightNumber-1);
    }

    // 获得某个用户的版权数量 通用接口
    function getUserCopyrightsNumber(address ethAddress) public view returns(uint) {
        return userCopyrightsNumber[ethAddress];
    }

    // 获得某个用户具体的某个版权信息 通用接口
    function getUserCopyright(address ethAddress,uint copyrightID) public view returns(uint,string memory, string memory, string memory, uint) {
        uint index = userCopyrightsIndex[ethAddress][copyrightID];
        Copyright storage c = copyrights[index];
        return (c.pictureID, c.pictureHash, c.waterMark, c.picturePath, c.createTime);
    }

    // 通用查询功能 通用接口
    function queryCopyrights(uint copyrightID) public view returns(uint,string memory, string memory, string memory,address, uint) {
        Copyright storage c = copyrights[copyrightID];
        return (c.pictureID, c.pictureHash, c.waterMark, c.picturePath, c.pictureOwner, c.createTime);
    }

}