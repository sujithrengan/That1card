package com.thehp.that1card;

/**
 * Created by HP on 02-06-2015.
 */
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


class FlushedInputStream extends FilterInputStream {

    public FlushedInputStream(final InputStream inputStream) {
        super(inputStream);
    }
   @Override
   public long skip(final long n) throws IOException {
        long totalBytesSkipped = 0L;
         while (totalBytesSkipped < n) {
               long bytesSkipped = in.skip(n - totalBytesSkipped);
             if (bytesSkipped == 0L) {
                   int bytesRead = read();
                if (bytesRead < 0) { // we reached EOF
                    break;
                }
               bytesSkipped = 1;
            }
            totalBytesSkipped += bytesSkipped;
        }
        return totalBytesSkipped;
    }
}