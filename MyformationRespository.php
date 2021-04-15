<?php

namespace App\Repository;

use App\Entity\Myformation;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Myformation|null find($id, $lockMode = null, $lockVersion = null)
 * @method Myformation|null findOneBy(array $criteria, array $orderBy = null)
 * @method Myformation[]    findAll()
 * @method Myformation[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class MyformationRespository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Myformation::class);
    }

    // /**
    //  * @return Myformation[] Returns an array of Myformation objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('m')
            ->andWhere('m.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('m.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Myformation
    {
        return $this->createQueryBuilder('m')
            ->andWhere('m.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
