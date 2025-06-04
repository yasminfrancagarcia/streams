
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    record Car(String type, String make,  String model, Integer engineCapacity) {}
    public static void main(String[] args) {
        List<Car> cars = List.of(
                new Car("sedan", "BMW", "530", 1998),
                new Car("sedan", "Audi", "A5", 1997),
                new Car("sedan", "mercedes", "E-class", 2500),
                new Car("sedan", "Toyota", "Light", 1600),
                new Car("hatchback", "jeep", "compass", 1800),
                new Car("hatchback", "fiat", "toro", 2000)
        );
        ///filter() vai retornar um stream dos carros do tipo sedan
        //precisamos converter a string para lista para então armazenar, para isso usa toList()
        List<Car> carros_sedan = cars.stream().filter(car -> car.type.equals("sedan")).toList();
        System.out.println("Carros sedan: ");
        System.out.println(carros_sedan);
        //System.out.println(cars.stream().filter(car -> car.type.equals("sedan")));
        // ^ não pode usar esse sout pq é o endereço do stream. Stream não tem dados diretamente
        // é apenas o fluxo de dados temporário, só gera dados quando executa uma operação no terminal

        //map() é usado para TRANSFORMAR elementos
        ///pega uma lista das MARCAS dos carros
        List<String> carMakeList = cars.stream().map(car -> car.make).toList(); //tranforma car em String
        System.out.println("Lista das marcas: ");
        System.out.println(carMakeList);

        //aqui, quero uma lista hibrida que tenha o modelo e o make
        ///Flatmap "achata" uma estrutura de dados em uma mais simples, nesse caso de make e model
        List<String> carMakeModelList = cars.stream().flatMap(car -> Stream.of(car.make, car.model)).toList();
        System.out.println("Lista achatada(hibirida) das marcas e modelos: ");
        System.out.println(carMakeModelList);

        ///filtrar carros com motor acima de 2000
        List<Car> highEngineCars = cars.stream()
                .filter(car -> car.engineCapacity > 2000)
                .collect(Collectors.toList());
        System.out.println("lista de carros com motor acima de 2000: ");
        System.out.println(highEngineCars);

        ///Encontrar carro com o menor motor

        //le recebe como argumento um comparador
        //(um objeto Comparator ou uma função lambda que define como comparar os itens).
        //Retorna um objeto Optional, que pode conter o menor elemento ou estar vazio se o stream estiver vazio.
        Car minEngineCar = cars.stream()
                .min((c1, c2) -> Integer.compare(c1.engineCapacity, c2.engineCapacity))
                .orElse(null);
        System.out.println("Carro com menor motor:");
        System.out.println(minEngineCar);
        /* //ou
        Car minEngineCar = cars.stream()
        .min(java.util.Comparator.comparingInt(car -> car.engineCapacity))
        .orElse(null);
        */

        ///verificar se existe algum carro da toyota
        boolean hasToyota = cars.stream()
                .anyMatch(car -> car.make.equalsIgnoreCase("Toyota"));
        System.out.println("Tem Toyota? " + hasToyota);

        ///soma da capacidade dos motores
        double totalEngineCapacity = cars.stream()
                .mapToDouble(car -> car.engineCapacity) // transforma Car em double (cilindrada)
                .sum();
        System.out.println("soma das cilindradas/capacidades : " + totalEngineCapacity);

        /// média das capacidades dos motores
        double avgEngineCapacity = cars.stream()
                .mapToDouble(car -> car.engineCapacity)
                .average()
                .orElse(0.0);
        System.out.println("média das cilindradas dos motores: " + avgEngineCapacity);

        ///ordenar carros por capacidade do motor crescente
        List<Car> sortedByEngine = cars.stream()
                .sorted(java.util.Comparator.comparingDouble(car -> car.engineCapacity))
                .toList();
        System.out.println("Ordenar carro por motor crescente");
        System.out.println(sortedByEngine);
        /// ordernar em ordem decrescente
        List<Car> sortedByEngineDesc = cars.stream()
                .sorted(Comparator.comparingInt(Car::engineCapacity).reversed()) //car -> car.engineCapacity()
                .toList();
        System.out.println("Ordenar carro por motor decrescente");
        System.out.println(sortedByEngineDesc);
        /// pegar os 3 primeiros carros
        List<Car> firstThreeCars = cars.stream()
                .limit(3)
                .toList();
        System.out.println(firstThreeCars);
        /// agrupar carros por tipo
        var carsByType = cars.stream()
                .collect(Collectors.groupingBy(car -> car.type));
        System.out.println("Carros por tipo sedan");
        System.out.println(carsByType);
        ///juntar todos os modelos em uma string separada por vírgula
        String allModels = cars.stream()
                .map(car -> car.model)
                .collect(Collectors.joining(", "));
        System.out.println("Modelos: " + allModels);




    }
}