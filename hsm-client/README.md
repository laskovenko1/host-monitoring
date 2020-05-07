# Host status monitor library client
HSM-client - a host's OS information library (HSM) client. It is a command-line software
that allows retrieving host's system information such as CPU, memory and filesystem info.

## Dependencies
<p>org.apache.commons:commons-cli-1.4</p>
<a href=https://github.com/laskovenko1/hsm/packages/184065>com.github.hsm:hsm-core-1.0.0 </a>

## Usage
<code>
java -jar hsm-client.jar [-C] [-d &lt;SECONDS&gt;] [-F &lt;TYPE_LIST&gt;] [-h] [-M &lt;VIRTUAL&gt;]
</code>

## Options
<p>-C,--cpu -- Enable CPU monitoring.</p>
<p>-d,--delay &lt;SECONDS&gt; -- Specifies the delay in SECONDS between screen updates.</p>
<p>-F,--fs &lt;TYPE_LIST&gt; -- Enable filesystem monitoring. TYPE_LIST argument used to limit listing to filesystems of
types in comma-separated TYPE_LIST. List all types if TYPE_LIST is omitted.
</p>                     
<p>
-h,--help -- Show help.
</p>              
<p>-M,--mem &lt;VIRTUAL&gt; -- Enable memory monitoring. VIRTUAL is a boolean arg used to enable virtual memory monitoring
(not monitored if VIRTUAL is omitted).
</p>

## Example
<code>
java -jar hsm-client.jar -d 2 -C -M true -F ext4,ext3,fat32
</code>
