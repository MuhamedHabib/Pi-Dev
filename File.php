<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\FileRespository;




/**
 * File
 *
 * @ORM\Table(name="file", indexes={@ORM\Index(name="fkey", columns={"id"})})
 * @ORM\Entity(repositoryClass=FileRespository::class)
 */
class File
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_file", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idFile;

    /**
     * @var string
     *
     * @ORM\Column(name="file", type="string", length=111, nullable=false)
     */
    private $file;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="date", nullable=false)
     */
    private $dateCreation;




    /**
     * @var string
     *
     * @ORM\Column(name="myfile", type="string", length=100, nullable=false)
     */
    private $myfile;

    /**
     * @var \Myformation
     *
     * @ORM\ManyToOne(targetEntity="Myformation")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id", referencedColumnName="id")
     * })
     */



    private $id;

    public function getIdFile(): ?int
    {
        return $this->idFile;
    }

    public function getFile(): ?string
    {
        return $this->file;
    }

    public function setFile(string $file): self
    {
        $this->file = $file;

        return $this;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->dateCreation;
    }

    public function setDateCreation(\DateTimeInterface $dateCreation): self
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    public function getMyfile(): ?string
    {
        return $this->myfile;
    }

    public function setMyfile(string $myfile): self
    {
        $this->myfile = $myfile;

        return $this;
    }

    public function getId(): ?Myformation
    {
        return $this->id;
    }

    public function setId(?Myformation $id): self
    {
        $this->id = $id;

        return $this;
    }


}
