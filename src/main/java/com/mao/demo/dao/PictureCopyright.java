package com.mao.demo.dao;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.6.4.
 */
@SuppressWarnings("rawtypes")
public class PictureCopyright extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600780546001600160a01b03191633179055611230806100326000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c80637ceffa58116100665780637ceffa58146105185780638249d3221461053557806387b162b5146106b35780639ba032f3146107dc578063ec2a6a31146108025761009e565b806325c2ae30146100a35780632b4e84131461022b578063365b98b2146102455780633b59f694146103605780636ec0c8aa14610510575b600080fd5b6100c0600480360360208110156100b957600080fd5b503561080a565b60405180878152602001806020018060200180602001866001600160a01b03166001600160a01b03168152602001858152602001848103845289818151815260200191508051906020019080838360005b83811015610129578181015183820152602001610111565b50505050905090810190601f1680156101565780820380516001836020036101000a031916815260200191505b5084810383528851815288516020918201918a019080838360005b83811015610189578181015183820152602001610171565b50505050905090810190601f1680156101b65780820380516001836020036101000a031916815260200191505b50848103825287518152875160209182019189019080838360005b838110156101e95781810151838201526020016101d1565b50505050905090810190601f1680156102165780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b610233610a0d565b60408051918252519081900360200190f35b6102626004803603602081101561025b57600080fd5b5035610a13565b60405180856001600160a01b03166001600160a01b031681526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156102c15781810151838201526020016102a9565b50505050905090810190601f1680156102ee5780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b83811015610321578181015183820152602001610309565b50505050905090810190601f16801561034e5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61050e6004803603606081101561037657600080fd5b810190602081018135600160201b81111561039057600080fd5b8201836020820111156103a257600080fd5b803590602001918460018302840111600160201b831117156103c357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561041557600080fd5b82018360208201111561042757600080fd5b803590602001918460018302840111600160201b8311171561044857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561049a57600080fd5b8201836020820111156104ac57600080fd5b803590602001918460018302840111600160201b831117156104cd57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610b5e945050505050565b005b610233610c9d565b6100c06004803603602081101561052e57600080fd5b5035610cb1565b6105616004803603604081101561054b57600080fd5b506001600160a01b038135169060200135610e90565b60405180868152602001806020018060200180602001858152602001848103845288818151815260200191508051906020019080838360005b838110156105b257818101518382015260200161059a565b50505050905090810190601f1680156105df5780820380516001836020036101000a031916815260200191505b50848103835287518152875160209182019189019080838360005b838110156106125781810151838201526020016105fa565b50505050905090810190601f16801561063f5780820380516001836020036101000a031916815260200191505b50848103825286518152865160209182019188019080838360005b8381101561067257818101518382015260200161065a565b50505050905090810190601f16801561069f5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b61050e600480360360408110156106c957600080fd5b810190602081018135600160201b8111156106e357600080fd5b8201836020820111156106f557600080fd5b803590602001918460018302840111600160201b8311171561071657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561076857600080fd5b82018360208201111561077a57600080fd5b803590602001918460018302840111600160201b8311171561079b57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061109c945050505050565b610233600480360360208110156107f257600080fd5b50356001600160a01b0316611142565b61023361115d565b6000818152600360208181526040808420805460048201546005830154600180850180548751601f60026000199584161561010002959095019092168490049182018a90048a0281018a019098528088526060998a998a998d998a99909890979596890195938901946001600160a01b0390911693928791908301828280156108d45780601f106108a9576101008083540402835291602001916108d4565b820191906000526020600020905b8154815290600101906020018083116108b757829003601f168201915b5050875460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959a50899450925084019050828280156109625780601f1061093757610100808354040283529160200191610962565b820191906000526020600020905b81548152906001019060200180831161094557829003601f168201915b5050865460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959950889450925084019050828280156109f05780601f106109c5576101008083540402835291602001916109f0565b820191906000526020600020905b8154815290600101906020018083116109d357829003601f168201915b505050505092509650965096509650965096505091939550919395565b60005481565b600160208181526000928352604092839020805481840180548651600261010097831615979097026000190190911695909504601f81018590048502860185019096528585526001600160a01b0390911694919392909190830182828015610abc5780601f10610a9157610100808354040283529160200191610abc565b820191906000526020600020905b815481529060010190602001808311610a9f57829003601f168201915b50505060028085018054604080516020601f6000196101006001871615020190941695909504928301859004850281018501909152818152959695945090925090830182828015610b4e5780601f10610b2357610100808354040283529160200191610b4e565b820191906000526020600020905b815481529060010190602001808311610b3157829003601f168201915b5050505050908060030154905084565b3360008181526005602090815260408083208054600280546006865284872083885286528487205582546001908101909355835160c08101855290548082528186018b81528286018b9052606083018a905260808301989098524260a0830152865260038552929094208251815594518051949592949293610be69392850192910190611163565b5060408201518051610c02916002840191602090910190611163565b5060608201518051610c1e916003840191602090910190611163565b5060808201516004820180546001600160a01b0319166001600160a01b0390921691909117905560a090910151600590910155600280546001810190915560408051338152602081019290925280517ff04d7d2e106aee5a60b674f11c7c61dc74a33b87d307443aa383e414224edde79281900390910190a150505050565b336000908152600460205260409020545b90565b6003602090815260009182526040918290208054600180830180548651600293821615610100026000190190911692909204601f810186900486028301860190965285825291949293909290830182828015610d4e5780601f10610d2357610100808354040283529160200191610d4e565b820191906000526020600020905b815481529060010190602001808311610d3157829003601f168201915b50505060028085018054604080516020601f6000196101006001871615020190941695909504928301859004850281018501909152818152959695945090925090830182828015610de05780601f10610db557610100808354040283529160200191610de0565b820191906000526020600020905b815481529060010190602001808311610dc357829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015610e705780601f10610e4557610100808354040283529160200191610e70565b820191906000526020600020905b815481529060010190602001808311610e5357829003601f168201915b50505050600483015460059093015491926001600160a01b031691905086565b6001600160a01b03821660009081526006602090815260408083208484528252808320548084526003808452828520805460058201546001808401805488516002938216156101000260001901909116839004601f81018b90048b0282018b019099528881526060998a998a998d9990989796949588019490880193909290918691830182828015610f635780601f10610f3857610100808354040283529160200191610f63565b820191906000526020600020905b815481529060010190602001808311610f4657829003601f168201915b5050865460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295995088945092508401905082828015610ff15780601f10610fc657610100808354040283529160200191610ff1565b820191906000526020600020905b815481529060010190602001808311610fd457829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529598508794509250840190508282801561107f5780601f106110545761010080835404028352916020019161107f565b820191906000526020600020905b81548152906001019060200180831161106257829003601f168201915b505050505091509650965096509650965050509295509295909350565b60008054338083526004602090815260408085208490558051608081018252928352828201878152838201879052426060850152600180860187559486528483529420825181546001600160a01b0319166001600160a01b039091161781559351805192949361111493908501929190910190611163565b5060408201518051611130916002840191602090910190611163565b50606082015181600301559050505050565b6001600160a01b031660009081526005602052604090205490565b60025481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106111a457805160ff19168380011785556111d1565b828001600101855582156111d1579182015b828111156111d15782518255916020019190600101906111b6565b506111dd9291506111e1565b5090565b610cae91905b808211156111dd57600081556001016111e756fea265627a7a723158205cccc161885e77ab4c6bdb2da4e480a728a6e29cfaf6e7e03f2e692e230ce52764736f6c63430005110032";

    public static final String FUNC_COPYRIGHTNUMBER = "copyrightNumber";

    public static final String FUNC_COPYRIGHTS = "copyrights";

    public static final String FUNC_CREATEUSER = "createUser";

    public static final String FUNC_GETUSERCOPYRIGHT = "getUserCopyright";

    public static final String FUNC_GETUSERCOPYRIGHTSNUMBER = "getUserCopyrightsNumber";

    public static final String FUNC_GETUSERINDEX = "getUserIndex";

    public static final String FUNC_QUERYCOPYRIGHTS = "queryCopyrights";

    public static final String FUNC_SETCOPYRIGHT = "setCopyright";

    public static final String FUNC_USERNUMBER = "userNumber";

    public static final String FUNC_USERS = "users";

    public static final Event SETCOPYRIGHTEVENT_EVENT = new Event("setCopyrightEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PictureCopyright(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PictureCopyright(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PictureCopyright(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PictureCopyright(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<SetCopyrightEventEventResponse> getSetCopyrightEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SETCOPYRIGHTEVENT_EVENT, transactionReceipt);
        ArrayList<SetCopyrightEventEventResponse> responses = new ArrayList<SetCopyrightEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SetCopyrightEventEventResponse typedResponse = new SetCopyrightEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ethAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SetCopyrightEventEventResponse> setCopyrightEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, SetCopyrightEventEventResponse>() {
            @Override
            public SetCopyrightEventEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SETCOPYRIGHTEVENT_EVENT, log);
                SetCopyrightEventEventResponse typedResponse = new SetCopyrightEventEventResponse();
                typedResponse.log = log;
                typedResponse.ethAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SetCopyrightEventEventResponse> setCopyrightEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETCOPYRIGHTEVENT_EVENT));
        return setCopyrightEventEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> copyrightNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COPYRIGHTNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, BigInteger>> copyrights(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COPYRIGHTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, String, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, String, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, String, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> createUser(String name, String email) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(email)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, String, String, String, BigInteger>> getUserCopyright(String ethAddress, BigInteger copyrightID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERCOPYRIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, ethAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(copyrightID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, String, String, String, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getUserCopyrightsNumber(String ethAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERCOPYRIGHTSNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, ethAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getUserIndex() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERINDEX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, BigInteger>> queryCopyrights(BigInteger copyrightID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_QUERYCOPYRIGHTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(copyrightID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, String, String, String, String, BigInteger>>(function,
                new Callable<Tuple6<BigInteger, String, String, String, String, BigInteger>>() {
                    @Override
                    public Tuple6<BigInteger, String, String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, String, String, String, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setCopyright(String picHash, String mark, String picturePath) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCOPYRIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(picHash), 
                new org.web3j.abi.datatypes.Utf8String(mark), 
                new org.web3j.abi.datatypes.Utf8String(picturePath)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> userNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<String, String, String, BigInteger>> users(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, String, BigInteger>>(function,
                new Callable<Tuple4<String, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static PictureCopyright load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PictureCopyright(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PictureCopyright load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PictureCopyright(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PictureCopyright load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PictureCopyright(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PictureCopyright load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PictureCopyright(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PictureCopyright> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PictureCopyright.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<PictureCopyright> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PictureCopyright.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PictureCopyright> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PictureCopyright.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PictureCopyright> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PictureCopyright.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SetCopyrightEventEventResponse extends BaseEventResponse {
        public String ethAddress;

        public BigInteger index;
    }
}
