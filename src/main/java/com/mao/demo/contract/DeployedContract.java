package com.mao.demo.contract;


import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;


public class DeployedContract {

	public static void main(String[] args) throws Exception {
		// 1.连接以太坊
		Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));
		// 2.通过私钥创建凭证
		Credentials credentials = Credentials.create("d79a156f560d5847892d1883bc6ea3e2cc77a8c8260f5285898eb1f5b037a98f");
		// 3.部署合约
		PictureCopyright contract = PictureCopyright.deploy(web3j, credentials,new DefaultGasProvider()).send();
		// 4.打印合约地址
		String address = contract.getContractAddress();
		// 5.输出地址
		System.out.println(address);
	}
}
