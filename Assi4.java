import java.util.*;

public class Assi4 {

    public static String decToBin(int n) {
        String bin = "";
        for (int i = 7; i >= 0; i--) {
            bin += ((n >> i) & 1) == 1 ? "1" : "0";
        }
        return bin;
    }

    public static int binToDec(String bin) {
        int val = 0;
        for (int i = 0; i < 8; i++) {
            val = val * 2 + (bin.charAt(i) - '0');
        }
        return val;
    }
      
    public static void splitIP(String ipStr, int[] arr) {
        String[] parts = ipStr.split("\\.");
        for (int i = 0; i < 4; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
    }

    public static void printIP(int[] arr) {
        System.out.println(arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3]);
    }

    public static int countBits(int[] mask) {
        int bits = 0;
        boolean stop = false;
        for (int i = 0; i < 4 && !stop; i++) {
            String bin = decToBin(mask[i]);
            for (char c : bin.toCharArray()) {
                if (c == '1' && !stop) {
                    bits++;
                } else {
                    stop = true;
                    break;
                }
            }
        }
        return bits;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String ipStr, maskStr;
        int[] ip = new int[4];
        int[] mask = new int[4];
        int[] net = new int[4];
        int[] broad = new int[4];
        int[] first = new int[4];
        int[] last = new int[4];

        System.out.print("Enter IP Address: ");
        ipStr = sc.nextLine();
        System.out.print("Enter Subnet Mask: ");
        maskStr = sc.nextLine();

         
        splitIP(ipStr, ip);
        splitIP(maskStr, mask); 
 
        for (int i = 0; i < 4; i++) {
            net[i] = ip[i] & mask[i];
        }

         for (int i = 0; i < 4; i++) {
            broad[i] = net[i] | (~mask[i] & 255);
        }

         for (int i = 0; i < 4; i++) {
            first[i] = net[i];
            last[i] = broad[i];
        }
        first[3] += 1;
        last[3] -= 1;

         int subnetBits = countBits(mask);
        int hostBits = 32 - subnetBits;
        int hostsPerSubnet = (int) Math.pow(2, hostBits) - 2;
        int numberOfSubnets = (int) Math.pow(2, subnetBits);

        
        System.out.print("\nNetwork ID: ");
        printIP(net);

        System.out.print("Broadcast Address: ");
        printIP(broad);

        System.out.print("Host Range: ");
        printIP(first);
        System.out.print(" - ");
        printIP(last);
        System.out.println();

        System.out.println("Number of Subnet Bits: " + subnetBits);
        System.out.println("Number of Host Bits: " + hostBits);
        System.out.println("Number of Hosts per Subnet: " + hostsPerSubnet);
        System.out.println("Number of Subnets possible: " + numberOfSubnets);

        sc.close();
}
}