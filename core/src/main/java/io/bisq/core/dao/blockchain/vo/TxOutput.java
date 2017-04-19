/*
 * This file is part of bisq.
 *
 * bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bisq.core.dao.blockchain.vo;

import io.bisq.common.app.Version;
import io.bisq.common.persistence.Persistable;
import io.bisq.common.util.JsonExclude;
import io.bisq.core.dao.blockchain.btcd.PubKeyScript;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Utils;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Slf4j
@Value
@Immutable
public class TxOutput implements Persistable {
    private static final long serialVersionUID = Version.LOCAL_DB_VERSION;

    private final int index;
    private final long value;
    private final String txId;
    private final PubKeyScript pubKeyScript;
    @Nullable
    @JsonExclude
    private final String address;
    @Nullable
    @JsonExclude
    private final byte[] opReturnData;
    private final int blockHeight;
    private final long time;

/*    public String getAddress() {
        String address = "";
        // Only at raw MS outputs addresses have more then 1 entry
        // We do not support raw MS for BSQ but lets see if is needed anyway, might be removed
        final List<String> addresses = pubKeyScript.getAddresses();
        if (addresses.size() == 1) {
            address = addresses.get(0);
        } else if (addresses.size() > 1) {
            final String msg = "We got a raw Multisig script. That is not supported for BSQ tokens.";
            log.warn(msg);
            address = addresses.toString();
            if (DevEnv.DEV_MODE)
                throw new RuntimeException(msg);
        } else {
            final String msg = "We got no address. Unsupported pubKeyScript";
            log.warn(msg);
            if (DevEnv.DEV_MODE)
                throw new RuntimeException(msg);
        }
        return address;
    }*/

    public String getId() {
        return txId + ":" + index;
    }

    public String getSortString() {
        return blockHeight + ":" + txId;
    }

    public TxIdIndexTuple getTxIdIndexTuple() {
        return new TxIdIndexTuple(txId, index);
    }

    @Override
    public String toString() {
        return "TxOutput{" +
                "\n     index=" + index +
                ",\n     value=" + value +
                ",\n     txId='" + txId + '\'' +
                ",\n     pubKeyScript=" + pubKeyScript +
                ",\n     blockHeight=" + blockHeight +
                ",\n     opReturnData=" + (opReturnData != null ? Utils.HEX.encode(opReturnData) : "null") +
                ",\n     time=" + time +
                "\n}";
    }
}
