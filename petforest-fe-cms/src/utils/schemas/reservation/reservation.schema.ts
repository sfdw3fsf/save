import * as yup from 'yup';
import { yups } from '../yups';

export const reservationSchema = yup.object().shape({
  guardianInfo: yup.object().shape({
    phoneNumber: yups.required.string('require.phone_number'),
    email: yups.custom.email(undefined, 'invalid.email'),
  }),
  serviceInfo: yup.object().shape({
    supplies: yups.custom.suppliesKind('require.supplyMaterial'),
    columbarium: yup.object().shape({
      isActive: yup.boolean().required(),
      roomNumber: yups.custom.roomNumber('require.room_number'),
    }),
  }),
  funeralInfo: yup.object().shape({
    transport: yup.object().shape({
      numberPeople: yups.custom.numberPeople('require.number_people'),
      transportTime: yups.custom.transportTime('require.transport_time'),
      deliveryServiceAddress: yups.custom.deliveryServiceAddress('require.delivery_service_address'),
    }),
  }),
});

export type ReservationSchemaType = yup.InferType<typeof reservationSchema>;
