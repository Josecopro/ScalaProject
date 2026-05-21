package delivery.models

// ==================== MODELOS DE DOMINIO ====================

sealed trait EstadoEntrega
case object EN_RUTA extends EstadoEntrega
case object ENTREGADO extends EstadoEntrega
case object DEVUELTO extends EstadoEntrega
case object CANCELADO extends EstadoEntrega
case object REPROGRAMADO extends EstadoEntrega

sealed trait TipoEvento
case object INICIO_RUTA extends TipoEvento
case object LLEGADA_DESTINO extends TipoEvento
case object RECHAZO_CLIENTE extends TipoEvento
case object CANCELACION extends TipoEvento
case object RETRASO_TRAFICO extends TipoEvento
case object CONSUMO_ANORMAL extends TipoEvento
case object RECLAMACION extends TipoEvento

// ==================== CASE CLASSES INMUTABLES ====================

case class Ubicacion(
  ciudad: String,
  latitud: Double,
  longitud: Double
)

case class Conductor(
  id: String,
  nombre: String,
  eficiencia: Double,
  horasLaborales: Int
)

case class Vehiculo(
  id: String,
  placa: String,
  capacidadKg: Double,
  consumoPromedio: Double
)

case class Cliente(
  id: String,
  nombre: String,
  ubicacion: Ubicacion,
  tipoCliente: String
)

case class Pedido(
  id: String,
  clienteId: String,
  peso: Double,
  valor: Double,
  origenUbicacion: Ubicacion,
  destinoUbicacion: Ubicacion
)

case class Entrega(
  id: String,
  pedidoId: String,
  conductorId: Option[String],
  vehiculoId: Option[String],
  estado: EstadoEntrega,
  fechaCreacion: Long,
  tiempoEstimado: Int,
  tiempoReal: Option[Int] = None,
  distanciaKm: Double
)

case class Evento(
  id: String,
  entregaId: String,
  tipoEvento: TipoEvento,
  timestamp: Long,
  ubicacion: Option[Ubicacion],
  detalles: Map[String, String]
)

case class Incidencia(
  id: String,
  entregaId: String,
  tipo: String,
  descripcion: String,
  timestamp: Long
)

case class Reclamacion(
  id: String,
  clienteId: String,
  entregaId: String,
  motivo: String,
  estado: String,
  fecha: Long
)

case class ConsumoVehiculo(
  vehiculoId: String,
  fecha: Long,
  litros: Double,
  costo: Double,
  kmRecorridos: Double
)

// ==================== MODELOS ANALÍTICOS ====================

case class MetricasConductor(
  conductorId: String,
  nombre: String,
  totalEntregas: Int,
  entregasExitosas: Int,
  totalDevoluciones: Int,
  promedio_retraso_minutos: Double,
  eficiencia_porcentaje: Double,
  ingresos_generados: Double
)

case class MetricasVehiculo(
  vehiculoId: String,
  placa: String,
  totalViajes: Int,
  totalKmRecorridos: Double,
  totalLitrosConsumidos: Double,
  consumoPromedio: Double,
  costoCombustible: Double,
  ingresos_operativos: Double,
  rentabilidad: Double,
  reporte_fallas: Int
)

case class MetricasCliente(
  clienteId: String,
  nombre: String,
  totalPedidos: Int,
  tasaCancelacion: Double,
  totalReclamaciones: Int,
  volumenCompra: Double,
  frecuenciaCompra: Int,
  tipoCliente: String
)

case class MetricasZona(
  ciudad: String,
  totalEntregas: Int,
  promedio_retraso: Double,
  tasaCancelacion: Double,
  rentabilidad: Double,
  indiceTrafico: Double
)

case class AlertaOperativa(
  tipo: String,
  severidad: String,
  descripcion: String,
  entidad: String,
  timestamp: Long
)

// ==================== MODELOS DE REPORTE ====================

case class ResumenEjecutivo(
  periodoAnalisis: String,
  totalEntregas: Int,
  tasaExito: Double,
  tasaCancelacion: Double,
  rentabilidad_total: Double,
  eficiencia_global: Double,
  alerta_critica: Int
)

case class ReporteAnalitico(
  resumen: ResumenEjecutivo,
  metricas_conductores: List[MetricasConductor],
  metricas_vehiculos: List[MetricasVehiculo],
  metricas_clientes: List[MetricasCliente],
  metricas_zonas: List[MetricasZona],
  alertas: List[AlertaOperativa],
  timestamp: Long
)
