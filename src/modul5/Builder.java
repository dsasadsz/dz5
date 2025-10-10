package modul5;


//разделяет процессa   мысал Computer comp = new Computer("Intel i9", "32GB", "1TB SSD", "RTX 3080", "Windows 11");


class Computer {
    private String CPU;
    private String RAM;
    private String Storage;
    private String GPU;
    private String OS;

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public void setStorage(String storage) {
        this.Storage = storage;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    @Override
    public String toString() {
        return "Компьютер: " +
                "CPU - " + CPU +
                ", RAM - " + RAM +
                ", Накопитель - " + Storage +
                ", GPU - " + GPU +
                ", ОС - " + OS;
    }
}



interface IComputerBuilder {
    void setCPU();
    void setRAM();
    void setStorage();
    void setGPU();
    void setOS();
    Computer getComputer();
}






class OfficeComputerBuilder implements IComputerBuilder {
    private Computer computer = new Computer();

    public void setCPU() {
        computer.setCPU("Intel i3");
    }

    public void setRAM() {
        computer.setRAM("8GB");
    }

    public void setStorage() {
        computer.setStorage("1TB HDD");
    }

    public void setGPU() {
        computer.setGPU("Integrated");
    }

    public void setOS() {
        computer.setOS("Windows 10");
    }

    public Computer getComputer() {
        return computer;
    }
}







class GamingComputerBuilder implements IComputerBuilder {
    private Computer computer = new Computer();

    public void setCPU() {
        computer.setCPU("Intel i9");
    }

    public void setRAM() {
        computer.setRAM("32GB");
    }

    public void setStorage() {
        computer.setStorage("1TB SSD");
    }

    public void setGPU() {
        computer.setGPU("NVIDIA RTX 3080");
    }

    public void setOS() {
        computer.setOS("Windows 11");
    }

    public Computer getComputer() {
        return computer;
    }
}




class ComputerDirector {
    private IComputerBuilder builder;

    public ComputerDirector(IComputerBuilder builder) {
        this.builder = builder;
    }

    public void constructComputer() {
        builder.setCPU();
        builder.setRAM();
        builder.setStorage();
        builder.setGPU();
        builder.setOS();
    }

    public Computer getComputer() {
        return builder.getComputer();
    }
}





public class Builder {
    public static void main(String[] args) {
        IComputerBuilder officeBuilder = new OfficeComputerBuilder();
        ComputerDirector director = new ComputerDirector(officeBuilder);
        director.constructComputer();
        Computer officeComputer = director.getComputer();
        System.out.println(officeComputer);

        IComputerBuilder gamingBuilder = new GamingComputerBuilder();
        director = new ComputerDirector(gamingBuilder);
        director.constructComputer();
        Computer gamingComputer = director.getComputer();
        System.out.println(gamingComputer);
    }
}
